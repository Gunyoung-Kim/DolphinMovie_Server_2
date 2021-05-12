package com.dolphinmovie.server.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.dolphinmovie.server.entity.Movie;

@Service("kobis")
public class KobisService {
	private ArrayList<Movie> dailyList;
	private ArrayList<Movie> weeklyList;
	
	public KobisService() {
		this.dailyList = new ArrayList<>();
		this.weeklyList = new ArrayList<>();
	}
	
	/*
	 * update dailyList and weeklyList
	 * 
	 */
	private void updateBoxoffice(BoxOfficeType type,DateTime targetDate) {
		
	}
	
	/*
	 *  Return methods for various information of box office movies
	 */
	
	public ArrayList<Movie> getDailyList() {
		return this.dailyList;
	}
	
	public ArrayList<Movie> getWeeklyList() {
		return this.weeklyList;
	}
	
	// movie names
	public ArrayList<String> getDailyListNames() {
		ArrayList<String> result = new ArrayList<>();
		if(this.dailyList == null)
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now());
			
		for(Movie movie: dailyList) {
			result.add(movie.getName());
		}
		return result;
	}
	
	public ArrayList<String> getWeeklyListNames() {
		ArrayList<String> result = new ArrayList<>();
		if(this.weeklyList == null) 
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now());
		
		for(Movie movie: weeklyList) {
			result.add(movie.getName());
		}
		
		return result;
	}
	
	// movie rankIntens
	public ArrayList<Integer> getDailyListRankIntens() {
		ArrayList<Integer> result = new ArrayList<>();
		if(this.dailyList == null)
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now());
			
		for(Movie movie: dailyList) {
			result.add(movie.getRankInten());
		}
		return result;
	}
	
	public ArrayList<Integer> getWeeklyListRankIntens() {
		ArrayList<Integer> result = new ArrayList<>();
		if(this.dailyList == null)
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now());
			
		for(Movie movie: weeklyList) {
			result.add(movie.getRankInten());
		}
		return result;
	}
	
	// movie Rank Old and News
	public ArrayList<Boolean> getDailyRankOldAndNews() {
		ArrayList<Boolean> result = new ArrayList<>();
		if(this.dailyList == null)
			updateBoxoffice(BoxOfficeType.Daily,DateTime.now());
		
		for(Movie movie: dailyList) {
			result.add(movie.isRankOldAndNew());
		}
		
		return result;
	}
	
	public ArrayList<Boolean> getWeeklyRankOldAndNews() {
		ArrayList<Boolean> result = new ArrayList<>();
		if(this.dailyList == null)
			updateBoxoffice(BoxOfficeType.Weekly,DateTime.now());
		
		for(Movie movie: weeklyList) {
			result.add(movie.isRankOldAndNew());
		}
		
		return result;
	}
	
	@PostConstruct
	public void init() {
		
	}
	
	private enum BoxOfficeType {
		Daily, Weekly;
	}
}
