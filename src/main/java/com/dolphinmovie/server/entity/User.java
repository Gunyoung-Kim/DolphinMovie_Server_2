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
@Table(name="dolphin_movie_user")
@NamedQueries ({
	@NamedQuery(name=User.FIND_BY_ADDRESS, query="select u from dolphin_movie_user u where u.address= :address"),
	@NamedQuery(name=User.FIND_ID_BY_ADDRESS,query="select u.id from dolphin_movie_user u where u.address = :address"),
	@NamedQuery(name=User.FIND_BY_ID,query="select u from dolphin_movie_user u where u.id = :id"),
	@NamedQuery(name=User.FIND_ALL, query="select u from dolphin_movie_user")
})
public class User {
	
	// names for namedQueries
	public static final String FIND_BY_ADDRESS = "User.findByAddress";
	public static final String FIND_ID_BY_ADDRESS = "User.findIdByAddress";
	public static final String FIND_BY_ID = "User.findById";
	public static final String FIND_ALL = "User.findAll";
	
	@Id
	@Column
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	@NotNull
	private String address;
	
	@Column(length=40)
	@NotNull
	private String name;
	
	@Column
	@Min(0)
	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}