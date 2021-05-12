package com.dolphinmovie.server.entity;

import java.net.URL;

public class Movie {
	
	private String name;
	
	// Rank increase or decrease compared to previous day
	private Integer rankInten;
	
	//  Whether it has entered the rankings this week.
	private boolean rankOldAndNew;
	
	private URL thumbnailLink;
	
	private URL link;

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getRankInten() {
		return rankInten;
	}


	public void setRankInten(Integer rankInten) {
		this.rankInten = rankInten;
	}


	public boolean isRankOldAndNew() {
		return rankOldAndNew;
	}


	public void setRankOldAndNew(boolean rankOldAndNew) {
		this.rankOldAndNew = rankOldAndNew;
	}


	public URL getThumbnailLink() {
		return thumbnailLink;
	}


	public void setThumbnailLink(URL thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}


	public URL getLink() {
		return link;
	}


	public void setLink(URL link) {
		this.link = link;
	}


	public Movie(String name, int rankInten, boolean rankOldAndNew, URL thumbnailLink, URL link) {
		super();
		this.name = name;
		this.rankInten = rankInten;
		this.rankOldAndNew = rankOldAndNew;
		this.thumbnailLink = thumbnailLink;
		this.link = link;
	}
	
	
}
