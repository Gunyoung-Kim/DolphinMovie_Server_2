package com.dolphinmovie.server.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dolphinmovie.server.entity.Movie;

@Service("naverMovieService")
public class NaverMovieService {
	
	Environment env;
	
	private KobisService kobis;
	
	private String[] dailyMoviesName;
	private String[] weeklyMoviesName;
	private LinkedList<Movie> dailyMoviesList;
	private LinkedList<Movie> weeklyMoviesList;
	
	/*
	 *  Constructors for this class 
	 */
	
	private NaverMovieService(KobisService kobis,Environment env) {
		this.env = env;
		this.kobis = kobis;
	}
	
	/*
	 * 
	 */
	
	private void makeMoviesList() {
		dailyMoviesName = kobis.getDailyListMovieName();
		weeklyMoviesName = kobis.getWeeklyListMovieName();
		int i=0;
		
		int[] dailyRankIntens = kobis.getDailyListMovieRankInten();
		int[] weeklyRankIntens = kobis.getWeeklyListMovieRankInten();
		
		int[] dailyOpenYear = kobis.getDailyListMovieOpenDateYear();
		int[] weeklyOpenYear = kobis.getWeeklyListMovieOpenDateYear();
		
		boolean[] dailyOldAndNew = kobis.getDailyListMovieRankOldAndNew();
		boolean[] weeklyOldAndNew = kobis.getWeeklyListMovieRankOldAndNew();
		
		LinkedList<Movie> tempDailyList = new LinkedList<>();
		for(String name: dailyMoviesName) {
			JSONObject o = getSingleMovieInfo(name,dailyOpenYear[i]);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			JSONArray arr = (JSONArray) o.get("items");
			
			if(arr != null && arr.size() >0) {
				JSONObject current_movie = (JSONObject) arr.get(0);
				String title = dailyMoviesName[i];
				String link = (String)current_movie.get("link");
				String thumbnailLink = (String)current_movie.get("image");
				int rankInten = dailyRankIntens[i];
				boolean rankOldAndNew = dailyOldAndNew[i++];
				
				URL thumbnailURL;
				try {
					thumbnailURL= new URL(thumbnailLink);
				} catch(MalformedURLException mex) {
					thumbnailURL = null;
				}
				
				URL linkURL;
				try {
					linkURL = new URL(link);
				} catch(MalformedURLException mex) {
					linkURL = null;
				}
				
				Movie c_movie = new Movie(title,rankInten,rankOldAndNew,thumbnailURL,linkURL);
				tempDailyList.add(c_movie);
			}
			
		}
		dailyMoviesList = tempDailyList;
		
		i=0;
		
		LinkedList<Movie> tempWeeklyList = new LinkedList<>();
		for(String name: weeklyMoviesName) {
			JSONObject o = getSingleMovieInfo(name,weeklyOpenYear[i]);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			JSONArray arr = (JSONArray) o.get("items");
			if(arr != null && arr.size() >0) {
				JSONObject current_movie = (JSONObject) arr.get(0);
				String title = weeklyMoviesName[i];
				String link = (String)current_movie.get("link");
				int rankInten = weeklyRankIntens[i];
				String thumbnailLink = (String)current_movie.get("image");
				boolean rankOldAndNew = weeklyOldAndNew[i++];
				
				URL thumbnailURL;
				try {
					thumbnailURL= new URL(thumbnailLink);
				} catch(MalformedURLException mex) {
					thumbnailURL = null;
				}
				
				URL linkURL;
				try {
					linkURL = new URL(link);
				} catch(MalformedURLException mex) {
					linkURL = null;
				}
				
				Movie c_movie = new Movie(title,rankInten,rankOldAndNew,thumbnailURL,linkURL);
				tempWeeklyList.add(c_movie);
			}
			
		}
		weeklyMoviesList = tempWeeklyList;
	}
	
	private JSONObject getSingleMovieInfo(String movieName,int openYear) {
		JSONObject result = null;
		try {
			String urlString = env.getProperty("navermovie_api");
			urlString += URLEncoder.encode(movieName, "UTF-8");
			urlString += "&display=1&yearfrom=" + (openYear-1) + "&yearto=" + openYear;
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty(env.getProperty("navermovie.api_id.key"), env.getProperty("navermovie.api_id.value"));
			connection.addRequestProperty(env.getProperty("navermovie.api_pw.key"), env.getProperty("navermovie.api_pw.value"));
			JSONParser parser = new JSONParser();
			result = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			if(connection.getResponseCode() != 200) {
				System.err.println(movieName+"Naver API Accident");
			}
		}	catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	
	/*
	 *  Getters for fields of this class 
	 */
	
	public String[] getDailyMoviesName() {
		return dailyMoviesName;
	}

	public String[] getWeeklyMoviesName() {
		return weeklyMoviesName;
	}

	public LinkedList<Movie> getDailyMoviesList() {
		return dailyMoviesList;
	}

	public LinkedList<Movie> getWeeklyMoviesList() {
		return weeklyMoviesList;
	}
	
	@PostConstruct
	private void init() {
		makeMoviesList();
	}
}
