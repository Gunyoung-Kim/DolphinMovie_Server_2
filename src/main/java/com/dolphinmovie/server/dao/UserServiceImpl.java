package com.dolphinmovie.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dolphinmovie.server.entity.User;

@Service("userService")
@Transactional
@Repository
public class UserServiceImpl implements UserService {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return em.createQuery(User.FIND_ALL,User.class).getResultList();
	}

	@Override
	public User saveUser(User user) {
		try {
			if(user.getId() == null) {
				em.persist(user);
				return user;
			} else {
				em.merge(user);
				return user;
			}
		} catch(Exception ex) {
			return null;
		}
	}

	@Override
	public void removeUser(User user) {
		User mergedContext = em.merge(user);
		em.remove(mergedContext);
	}

	@Override
	@Transactional(readOnly=true)
	public User findById(String id){
		try {
			User result = em.createNamedQuery(User.FIND_BY_ID,User.class).setParameter("id", id).getSingleResult();
			return result;
		} catch(NoResultException ex) {
			return null;
		}
	}

	/*
	 *  guaranteed that input id exist in database before this method is called
	 */
	@Override
	public User checkPassword(String id, String password) {
		User user = findById(id);
		
		if(password.equals(user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

	

}
