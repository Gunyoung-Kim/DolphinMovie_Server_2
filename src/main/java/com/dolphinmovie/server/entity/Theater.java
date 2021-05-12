package com.dolphinmovie.server.entity;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="dolphin_movie_theater")
@NamedQuery(name=Theater.FIND_ALL, query="select t from dolphin_movie_theater")
public class Theater {
	
	public static final String FIND_ALL = "Theater.findAll";
	
	@Id
	@Column
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=60, nullable = false)
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String address;
	
	@Column
	private Double xpos;
	
	@Column
	private Double ypos;
	
	@Column
	private boolean open;
	
	@Column
	private URL link;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getXpos() {
		return xpos;
	}

	public void setXpos(Double xpos) {
		this.xpos = xpos;
	}

	public Double getYpos() {
		return ypos;
	}

	public void setYpos(Double ypos) {
		this.ypos = ypos;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
	}

	
}