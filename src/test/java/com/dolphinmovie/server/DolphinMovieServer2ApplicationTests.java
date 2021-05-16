package com.dolphinmovie.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.dolphinmovie.server.config.AppConfig;

@SpringBootTest
@Import(AppConfig.class)
class DolphinMovieServer2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
