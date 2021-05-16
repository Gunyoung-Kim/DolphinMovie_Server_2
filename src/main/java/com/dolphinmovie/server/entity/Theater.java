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
@Table(name="Theater")
@NamedQuery(name=Theater.FIND_ALL, query="select t from Theater t")
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
	
	/*
	 * Constructor of this Class
	 */
	
	public Theater(@NotNull String name, @NotNull String address) {
		
	}
	
	public Theater(@NotNull String name, @NotNull String address, URL link) {
		this(name,address);
		this.link = link;
	}
	
	public Theater(@NotNull String name, @NotNull String address, Double xpos, Double ypos, boolean open, URL link) {
		this(name,address,link);
		this.xpos = xpos;
		this.ypos = ypos;
		this.open = open;
		
	}


	/*
	 *  Getters and Setters for fields of this Class 
	 */
	
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
	
	/*
	 * Theater which is not opened contains parentheses in its name
	 * 
	 * Opened -> return -1 ,  Not Opened yet -> return index of '(' to format its name
	 */
	private static int isOpened(String name) {
		char[] c = name.toCharArray();
		if(c[c.length-1] != ')') {
			return -1;
		} 
		
		for(int i=0;i<c.length;i++) {
			if(c[i] == '(') {
				return i;
			}
		}
		return -1;
	}
	
	
}