package org.sample.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sample.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired(required=true)
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserRole save(UserRole role) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(role);
		session.flush();
		return role;
	}

}