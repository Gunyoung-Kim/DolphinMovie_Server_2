package com.dolphinmovie.server.controller;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dolphinmovie.server.dao.TheaterService;
import com.dolphinmovie.server.entity.Theater;

@RestController
public class TheaterInfoController {
	
	@Autowired
	private TheaterService theaterService;
	
	@GetMapping(value="/theater")
	public String getTheaterInfo() {
		String result = "";
		List<Theater> theaterList = theaterService.getAll();
		
		result = processTheaterInfo(theaterList);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String processTheaterInfo(List<Theater> list) {
		JSONObject json = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		JSONArray theaters = new JSONArray();
		
		for(Theater t : list) {
			JSONObject o = t.toJSONObject();
			
			theaters.add(o);
		}
		
		resultJSON.put("theaters", theaters);
		json.put("theaterResult", resultJSON);
		
		String result = json.toJSONString();
		
		return result;
	}
}
