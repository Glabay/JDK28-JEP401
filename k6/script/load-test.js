import http from 'k6/http';
import {check, sleep} from 'k6';

const BASE_URL = (__ENV.BASE_URL || 'http://localhost:8080').replace(/\/$/, '');
const RUN_ID = __ENV.RUN_ID || `sensor-test-${Date.now()}`;

const MIN_GLUCOSE = 4.5;
const MAX_GLUCOSE = 25.0;

const READING_INTERVAL = 10;
const MIN_POLL_INTERVAL = 10;
const MAX_POLL_INTERVAL = 20;

export const options = {
    scenarios: {
        sensor_load: {
            executor: 'ramping-vus',
            startVUs: 1,
            stages: [
                {duration: '30s', target: 10},
                {duration: '30s', target: 100},
                {duration: '30s', target: 1000},
                {duration: '30s', target: 2500},
                {duration: '5m', target: 2500},
            ],
            gracefulRampDown: '30s',
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
    },
};

const jsonParams = {
    headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
    },
};

// State is isolated per VU and persists across iterations.
const sensorState = {
    sensorId: null,
    sequenceNumber: 1,
    glucoseMgDl: 0,
    nextReadingAt: 0,
    nextPollAt: 0,
};

function randomBetween(min, max) {
    return min + Math.random() * (max - min);
}

function localDateTime() {
    return new Date().toISOString().slice(0, 19);
}

function registerSensor() {
    const expiresAt = new Date(
        Date.now() + 30 * 24 * 60 * 60 * 1000
    );

    const payload = {
        serialNumber: `${RUN_ID}-sensor-${__VU}`,
        manufacturer: 'Midnight Coder',
        model: 'CGM-LoadTest-01',
        expiresAt: expiresAt.toISOString().slice(0, 19),
    };

    const response = http.post(
        `${BASE_URL}/api/v1/sensors`,
        JSON.stringify(payload),
        {
            ...jsonParams,
            tags: {operation: 'register_sensor'},
        }
    );

    const valid = check(response, {
        'sensor registration successful': (r) => r.status === 201,
    });

    if (!valid) {
        console.error(
            `Sensor registration failed for VU ${__VU}: ${response.status} ${response.body}`
        );
        return null;
    }

    try {
        const result = JSON.parse(response.body);
        return result.sensorId;
    } catch (_) {
        console.error(`Invalid registration response: ${response.body}`);
        return null;
    }
}

// Generates a random change of 0.5 to 1.5,
// increasing or decreasing while respecting the range.
function nextGlucoseValue(previous) {
    const change = randomBetween(0.5, 1.5);
    const direction = Math.random() < 0.5 ? -1 : 1;

    let next = previous + (change * direction);

    // Reverse direction at boundaries to maintain the change range.
    if (next < MIN_GLUCOSE || next > MAX_GLUCOSE) {
        next = previous - (change * direction);
    }

    return Number(next.toFixed(2));
}

function submitReading() {
    sensorState.sequenceNumber++;

    sensorState.glucoseMgDl = nextGlucoseValue(
        sensorState.glucoseMgDl
    );

    const payload = {
        sequenceNumber: sensorState.sequenceNumber,
        capturedAt: localDateTime(),
        glucoseMgDl: sensorState.glucoseMgDl,
    };

    const response = http.post(
        `${BASE_URL}/api/v1/readings/register/${sensorState.sensorId}/`,
        JSON.stringify(payload),
        {
            ...jsonParams,
            tags: {operation: 'submit_reading'},
        }
    );

    check(response, {
        'reading accepted': (r) => r.status === 200,
    });

    if (response.status !== 200) {
        console.error(
            `Reading submission failed: ${response.status} ${response.body}`
        );
    }
}

function requestLatestReading() {
    const response = http.get(
        `${BASE_URL}/api/v1/readings/latest/${sensorState.sensorId}`,
        {
            headers: {Accept: 'application/json'},
            tags: {operation: 'get_latest_reading'},
        }
    );

    check(response, {
        'latest reading retrieved': (r) => r.status === 200,
    });
}

export default function () {
    if (!sensorState.sensorId) {
        sensorState.sensorId = registerSensor();

        if (!sensorState.sensorId) {
            sleep(5);
            return;
        }
        sensorState.sequenceNumber = 1;
        sensorState.glucoseMgDl = Number(
            randomBetween(MIN_GLUCOSE, MAX_GLUCOSE).toFixed(2)
        );

        sensorState.nextReadingAt = 0;
        sensorState.nextPollAt = 0;
    }

    if (!sensorState.sensorId) {
        sleep(5);
        return;
    }

    while (true) {
        const now = Date.now() / 1000;

        if (now >= sensorState.nextReadingAt) {
            submitReading();
            sensorState.nextReadingAt =
                now + READING_INTERVAL;
        }

        if (now >= sensorState.nextPollAt) {
            requestLatestReading();
            sensorState.nextPollAt =
                now + randomBetween(
                    MIN_POLL_INTERVAL,
                    MAX_POLL_INTERVAL
                );
        }

        // Sleep until the next scheduled operation.
        const nextAction = Math.min(
            sensorState.nextReadingAt,
            sensorState.nextPollAt
        );

        sleep(Math.max(0.1, nextAction - Date.now() / 1000));
    }
}
