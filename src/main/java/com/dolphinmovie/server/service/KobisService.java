package com.dolphinmovie.server.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("kobisService")
public class KobisService {

	Environment env;
	
	/*
	 * Strings for connecting at kobis open api which is injected in postconstruct
	 */
	private String KOBIS_KEY;
	private String KOBIS_DAILY_URL;
	private String KOBIS_WEEKLY_URL;
	
	private JSONArray dailyList;
	private JSONArray weeklyList;
	
	private RestTemplate restTemplate;
	
	private KobisService(Environment env) {
		this.env = env;
		this.restTemplate = new RestTemplate();
		KOBIS_KEY = env.getProperty("kobis.api_key");
		KOBIS_DAILY_URL = env.getProperty("kobis.boxoffice.daily");
		KOBIS_WEEKLY_URL = env.getProperty("kobis.boxoffice.weekly");
		
		updateBoxoffice(BoxOfficeType.Daily,DateTime.now().minusDays(7));
		updateBoxoffice(BoxOfficeType.Weekly,DateTime.now().minusDays(7));
	}
	
	/*
	 * Update dailyList and weeklyList
	 * 
	 */
	public void updateBoxoffice(BoxOfficeType type,DateTime targetDate)  {
		String url;
		if(type == BoxOfficeType.Daily) {
			url = KOBIS_DAILY_URL;
		} else {
			url = KOBIS_WEEKLY_URL;
		}
		Map<String,String> queryMap = new HashMap<>();
		queryMap.put("key", KOBIS_KEY);
		queryMap.put("targetDt", targetDate.toString("yyyyMMdd"));
		HttpHeaders header = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(header);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity, String.class,queryMap);
		
		String responseString = response.getBody().toString();
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject parseResult = (JSONObject) parser.parse(responseString);
			JSONObject jsonResult = (JSONObject) parseResult.get("boxOfficeResult");
			if(jsonResult == null) {
				throw new MalformedURLException();
			}
			JSONArray list;
			
			if(type == BoxOfficeType.Daily) {
				list = (JSONArray) jsonResult.get("dailyBoxOfficeList");
				dailyList = list;
			} else {
				list = (JSONArray) jsonResult.get("weeklyBoxOfficeList");
				weeklyList = list;
			}
		} catch(MalformedURLException mex) {
			mex.printStackTrace();
		} catch (ParseException pex) {
			pex.printStackTrace();
		}
	}
	
	/*
	 *  Return methods for various information of box office movies
	 */
	
	public JSONArray getDailyList() {
		return this.dailyList;
	}
	
	public JSONArray getWeeklyList() {
		return this.weeklyList;
	}
	
	public String[] getDailyListMovieName() {
		if(dailyList == null) {
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now().minusDays(7));
		}
		String[] arr = new String[dailyList.size()];
		int i=0;
		for(Object o: dailyList) {
			JSONObject jo = (JSONObject) o;
			String name = (String) jo.get("movieNm");
			arr[i++] = name;
		}
		return arr;
	}
	
	public String[] getWeeklyListMovieName() {
		if(weeklyList == null) {
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now().minusDays(7));
		}
		String[] arr = new String[weeklyList.size()];
		int i=0;
		for(Object o: weeklyList) {
			JSONObject jo = (JSONObject) o;
			String name = (String) jo.get("movieNm");
			arr[i++] = name;
		}
		return arr;
	}
	
	public int[] getDailyListMovieRankInten() {
		if(dailyList == null) {
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now().minusDays(7));
		}
		int[] arr = new int[dailyList.size()];
		int i=0;
		for(Object o: dailyList) {
			JSONObject jo = (JSONObject) o;
			int rankInten = Integer.parseInt((String) jo.get("rankInten"));
			arr[i++] = rankInten;
		}
		return arr;
	}
	
	public int[] getWeeklyListMovieRankInten() {
		if(weeklyList == null) {
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now().minusDays(7));
		}
		int[] arr = new int[weeklyList.size()];
		int i=0;
		for(Object o: weeklyList) {
			JSONObject jo = (JSONObject) o;
			int rankInten = Integer.parseInt((String) jo.get("rankInten"));
			arr[i++] = rankInten;
		}
		return arr;
	}
	
	public boolean[] getDailyListMovieRankOldAndNew() {
		if(dailyList == null) {
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now().minusDays(7));
		}
		boolean[] arr = new boolean[dailyList.size()];
		int i=0;
		for(Object o: dailyList) {
			JSONObject jo = (JSONObject) o;
			String str = (String) jo.get("rankOldAndNew");
			if(str.equals("OLD")) {
				arr[i++] = false;
			} else {
				arr[i++] = true;
			}
		}
		
		return arr;
	}
	
	public boolean[] getWeeklyListMovieRankOldAndNew() {
		if(weeklyList == null) {
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now().minusDays(7));
		}
		boolean[] arr = new boolean[weeklyList.size()];
		int i=0;
		for(Object o: weeklyList) {
			JSONObject jo = (JSONObject) o;
			String str = (String) jo.get("rankOldAndNew");
			if(str.equals("OLD")) {
				arr[i++] = false;
			} else {
				arr[i++] = true;
			}
		}
		
		return arr;
	}
	
	public int[] getDailyListMovieOpenDateYear() {
		if(dailyList == null) {
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now().minusDays(7));
		}
		int[] arr = new int[dailyList.size()];
		int i = 0;
		for(Object o: dailyList) {
			JSONObject jo = (JSONObject) o;
			String str = (String) jo.get("openDt");
			arr[i++] = Integer.parseInt(str.substring(0, 4));
		}
		
		return arr;
	}
	
	public int[] getWeeklyListMovieOpenDateYear() {
		if(weeklyList == null) {
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now().minusDays(7));
		}
		int[] arr = new int[weeklyList.size()];
		int i = 0;
		for(Object o: weeklyList) {
			JSONObject jo = (JSONObject) o;
			String str = (String) jo.get("openDt");
			arr[i++] = Integer.parseInt(str.substring(0, 4));
		}
		
		return arr;
	}

	
	@PostConstruct
	private void init() {
	}
	
	private enum BoxOfficeType {
		Daily, Weekly;
	}
}
