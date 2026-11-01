package dev.midnightcoder.values;

import java.util.Objects;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-30
 */
public class Main {
    private value record Position(int x, int y, int z) {}

    void main() {
        var pokey = new Position(1, 2, 3);
        var glabay = new Position(1, 2, 3);

        IO.println(
            """
            pokey == glabay: %s
            pokey.equals(glabay): %s
            Objects.hasIdentity(pokey): %s
            """.formatted(
                pokey == glabay,
                pokey.equals(glabay),
                Objects.hasIdentity(pokey)
            )
        );
    }
}
