package dev.midnightcoder.cgm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void verifiesModuleStructure() {
        var modules = ApplicationModules
            .of(Application.class)
            .verify();

//        new Documenter(modules)
//            .writeModulesAsPlantUml()
//            .writeIndividualModulesAsPlantUml();
    }
}
