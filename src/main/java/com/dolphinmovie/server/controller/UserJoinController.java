package com.dolphinmovie.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * 
 * 
 */
@RestController
public class UserJoinController {
	
	//Use to check for duplicates of IDs when subscribing from a client
	@RequestMapping(name="/join/id_confirm", method=RequestMethod.POST)
	public String isIdExists() {
		String str = "";
		
		return str;
	}
	
	@RequestMapping(name="/join",method=RequestMethod.POST) 
	public String registerUser() {
		String str ="";
		return str;
	}
}
