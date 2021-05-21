package com.dolphinmovie.server.entity;

import java.net.URL;

import org.json.simple.JSONObject;

public class Movie {
	
	private String name;
	
	// Rank increase or decrease compared to previous day
	private Integer rankInten;
	
	//  Whether it has entered the rankings this week.
	private boolean rankOldAndNew;
	
	private URL thumbnailLink;
	
	private URL link;
	
	/*
	 * Constructor of this Class
	 */
	
	public Movie(String name) {
		this.name = name;
	}
	
	public Movie(String name,int rankInten, boolean rankOldAndNew) {
		this(name);
		this.rankInten = rankInten;
		this.rankOldAndNew = rankOldAndNew;
	}
	
	public Movie(String name, URL thumbnailLink, URL link) {
		this(name);
		this.thumbnailLink = thumbnailLink;
		this.link = link;
	}
	
	public Movie(String name, int rankInten, boolean rankOldAndNew, URL thumbnailLink, URL link) {
		this(name,rankInten,rankOldAndNew);
		this.thumbnailLink = thumbnailLink;
		this.link = link;
	}
	
	/*
	 *  Getters and Setters for fields of this Class 
	 */
	
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
	
	/*
	 *  return json style String of this class
	 */
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		
		object.put("title", this.name);
		object.put("rankInten", this.rankInten);
		object.put("rankOldAndNew", this.rankOldAndNew);
		object.put("thumbnailLink", thumbnailLink.toString());
		object.put("link", this.link.toString());
		
		return object;
	}

}
