package dev.midnightcoder.values;

import dev.midnightcoder.cgm.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class Jdk28Jep401ApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void verifiesModuleStructure() {

        ApplicationModules.of(
            Application.class
        ).verify();

        var modules = ApplicationModules
            .of(Application.class)
            .verify();

        new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml();
    }
}
