package com.dolphinmovie.server.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dolphinmovie.server.entity.Theater;

@Service("theaterService")
@Transactional
@Repository
public class TheaterServiceImpl implements TheaterService {
	
	@PersistenceContext
	EntityManager em;
	
	private List<Theater> theaterList;

	@Override
	@Transactional(readOnly=true)
	public List<Theater> getAll() {
		return em.createNamedQuery(Theater.FIND_ALL,Theater.class).getResultList();
	}

	@Override
	public void saveTheaters(List<Theater> list) {
		for(Theater theater: list) {
			saveTheater(theater);
		}
	}

	@Override
	public void saveTheater(Theater theater) {
		if(theater.getId() == null) {
			em.persist(theater);
		} else {
			em.merge(theater);
		}
	}
	
	
	public List<Theater> getTheaterList() {
		if(theaterList == null) {
			theaterList = getAll();
		} else if(theaterList.size() ==0) {
			theaterList = getAll();
		} 
		
		return this.theaterList;
	}
	
	@PostConstruct 
	public void init() {
		
	}

}
