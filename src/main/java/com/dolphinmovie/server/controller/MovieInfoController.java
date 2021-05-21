package com.dolphinmovie.server.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dolphinmovie.server.entity.Movie;
import com.dolphinmovie.server.service.CurrentScreeningService;
import com.dolphinmovie.server.service.NaverMovieService;

@RestController
public class MovieInfoController {
	
	@Autowired
	private NaverMovieService naverMovieService;
	
	@Autowired
	private CurrentScreeningService currentScreeningService;
	
	@GetMapping(value="rank")
	public String getMovieInfo() {
		List<Movie> dailyList = naverMovieService.getDailyMoviesList();
		List<Movie> weeklyList = naverMovieService.getWeeklyMoviesList();
		List<Movie> currentList = currentScreeningService.getScreeingMovies();
		
		return movieListToJSONObject(dailyList,weeklyList,currentList);
	}
	
	@SuppressWarnings("unchecked")
	private static String movieListToJSONObject(List<Movie> daily, List<Movie> weekly, List<Movie> screening) {
		JSONObject json = new JSONObject();
		
		JSONArray dailyArr = new JSONArray();
		for(Movie movie: daily) {
			JSONObject movie_json = movie.toJSONObject();
			dailyArr.add(movie_json);
		}
		
		JSONArray weeklyArr = new JSONArray();
		for(Movie movie: weekly) {
			JSONObject movie_json = movie.toJSONObject();
			weeklyArr.add(movie_json);
		}
		
		JSONArray screeningArr = new JSONArray();
		for(Movie movie: screening) {
			JSONObject movie_json = movie.toJSONObject();
			screeningArr.add(movie_json);
		}
		
		JSONObject result = new JSONObject();
		result.put("dailyMovies", dailyArr);
		result.put("weeklyMovies", weeklyArr);
		result.put("screeningMovies", screeningArr);
		json.put("boxofficeResult", result);
		
		return json.toString();
	}
}
