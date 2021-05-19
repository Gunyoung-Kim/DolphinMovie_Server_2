package com.dolphinmovie.server.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dolphinmovie.server.dao.UserService;
import com.dolphinmovie.server.entity.User;

/*
 * 
 * 
 */
@RestController
@RequestMapping(value="/join")
public class UserJoinController {
	
	@Autowired
	UserService userService;
	
	/*
	 * Use to check for duplicates of IDs when subscribing from a client
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/id_confirm")
	public String isIdExists(@RequestParam String id) {
		System.out.println(id);
		User result = userService.findById(id);
		JSONObject responseJSON = new JSONObject();
		if(result != null) {
			responseJSON.put("result_code", HttpStatus.OK);
			responseJSON.put("result","EXIST");
		} else {
			responseJSON.put("result_code", HttpStatus.OK);
			responseJSON.put("result","NON_EXIST");
		}
		
		return responseJSON.toString();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="") 
	public String registerUser(@ModelAttribute User user) {
		User result = userService.saveUser(user);
		
		JSONObject responseJSON = new JSONObject();
		
		if(result != null) {
			responseJSON.put("result_code", HttpStatus.CREATED);
			responseJSON.put("result", "SUCCESS");
			JSONObject user_info = new JSONObject();
			user_info.put("id", result.getId());
			user_info.put("password", result.getPassword());
			user_info.put("name", result.getName());
			responseJSON.put("error_msg", "welcome!");
			responseJSON.put("user_info",user_info);
		} else {
			responseJSON.put("result_code", HttpStatus.INTERNAL_SERVER_ERROR);
			responseJSON.put("result", "FAIL");
			JSONObject user_info = new JSONObject();
			user_info.put("id", "");
			user_info.put("password", "");
			user_info.put("name", "");
			responseJSON.put("error_msg", "Insufficient Storage");
			responseJSON.put("user_info",user_info);
		}
		
		return responseJSON.toString();
	}
	
}
