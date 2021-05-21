package com.dolphinmovie.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheaterInfoController {
	
	@GetMapping
	public String getTheaterInfo() {
		String result = "";
		
		return result;
	}
}
