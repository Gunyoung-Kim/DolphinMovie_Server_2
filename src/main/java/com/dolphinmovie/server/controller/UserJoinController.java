package com.dolphinmovie.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * 
 * 
 */
@RestController
@RequestMapping(value="/join")
public class UserJoinController {
	
	//Use to check for duplicates of IDs when subscribing from a client
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value="/id_confirm")
	public String isIdExists() {
		String str = "";
		
		return str;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value="/",method=RequestMethod.POST) 
	public String registerUser() {
		String str ="";
		return str;
	}
}
