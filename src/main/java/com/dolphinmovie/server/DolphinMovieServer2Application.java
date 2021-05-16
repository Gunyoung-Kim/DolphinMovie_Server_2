package com.dolphinmovie.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.dolphinmovie.server.config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class DolphinMovieServer2Application {

	public static void main(String[] args) {
		SpringApplication.run(DolphinMovieServer2Application.class, args);
	}

}
