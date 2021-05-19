package com.dolphinmovie.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="User")
@NamedQueries ({
	@NamedQuery(name=User.FIND_BY_ID,query="select u from User u where u.id = :id"),
	@NamedQuery(name=User.FIND_ALL, query="select u from User u")
})
public class User {
	
	// names for namedQueries
	public static final String FIND_BY_ID = "User.findById";
	public static final String FIND_ALL = "User.findAll";
	
	@Id
	@Column
	@NotNull
	private String id;
	
	@Column
	@NotNull
	private String password;
	
	@Column(length=40)
	@NotNull
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}