package com.dolphinmovie.server.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dolphinmovie.server.dao.UserService;
import com.dolphinmovie.server.entity.User;
import com.dolphinmovie.server.service.LoginService;

@RestController
public class UserLoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/login")
	public String login(@ModelAttribute User user) {
		User result = userService.findById(user.getId());
		
		JSONObject responseJSON = new JSONObject();
		
		if(result == null) {
			responseJSON.put("result_code", HttpStatus.OK);
			responseJSON.put("result", "NO_ACCOUNT");
			responseJSON.put("error_msg", "존재하지 않는 사용자입니다.");
		} else {
			result = userService.checkPassword(user.getId(), user.getPassword());
			if(result == null) {
				responseJSON.put("result_code", HttpStatus.OK);
				responseJSON.put("result", "INCORRECTPW");
				responseJSON.put("error_msg", "잘못된 비밀번호입니다.");
			} else {
				JSONObject memberjson = new JSONObject();
				memberjson.put("name", result.getName());
				memberjson.put("id", result.getId());
				responseJSON.put("user_info", memberjson);
				responseJSON.put("result_code", 0);
				responseJSON.put("result", "SUCCESS");
				responseJSON.put("error_msg", "정상적으로 처리되었습니다.");
				loginService.login(result);
			}
		}
		
		return responseJSON.toString();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/logout") 
	public String logout(@RequestParam String id) {
		User logoutResult = loginService.logout(id);
		
		JSONObject responseJSON = new JSONObject();
		if(logoutResult != null) {
			responseJSON.put("result_code", HttpStatus.OK);
			responseJSON.put("result", "SUCCESS");
			responseJSON.put("error_msg", "정상적으로 처리되었습니다.");
		} else {
			responseJSON.put("result_code", HttpStatus.NOT_FOUND);
			responseJSON.put("result", "FAIL");
			responseJSON.put("error_msg", "fail");
		}
		
		return responseJSON.toString();
	}
}
