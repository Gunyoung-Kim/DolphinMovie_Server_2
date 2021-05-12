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
	@Transactional(readOnly=true)
	public User findByAddress(String address) {
		return em.createNamedQuery(User.FIND_BY_ADDRESS, User.class).setParameter("address", address).getSingleResult();
	}

	@Override
	public void saveUser(User user) {
		if(user.getId() == null) {
			em.persist(user);
		} else {
			em.merge(user);
		}
	}

	@Override
	public void removeUser(User user) {
		User mergedContext = em.merge(user);
		em.remove(mergedContext);
	}

	@Override
	@Transactional(readOnly=true)
	public Long findIdByAddress(String address) {
		return em.createNamedQuery(User.FIND_ID_BY_ADDRESS,Long.class).setParameter("address", address).getSingleResult();
	}

	@Override
	@Transactional(readOnly=true)
	public User findById(Long id) throws Exception{
		try {
			User result = em.createNamedQuery(User.FIND_BY_ID,User.class).setParameter("id", id).getSingleResult();
			return result;
		} catch(NoResultException ex) {
			throw ex;
		}
	}

	

}
