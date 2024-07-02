package com.cobistopaz.prueba_tecnica;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PruebaTecnicaApplicationTests {

	@Test
	void contextLoads() {
		String asd = "asd";
		assertThat(asd).isNotNull();
	}

}
