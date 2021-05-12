package com.dolphinmovie.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.dolphinmovie.server.entity.Movie;

@Service("naverMovie")
@DependsOn("kobis")
public class NaverMovieService {
	
	private KobisService kobis;
	
	@Autowired
	public void setKobisService(KobisService kobis) {
		this.kobis = kobis;
	}
	
	private Movie addMovieInfoFromNaverMovieAPI(Movie movie) {
		Movie result = null;
		
		return result;
	}

}
