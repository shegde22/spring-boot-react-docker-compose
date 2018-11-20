package com.cs532.project2.srrest.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs532.project2.srrest.entity.Log;
import com.cs532.project2.srrest.entity.TA;

@Repository
public class LogDAO implements LogDAOI {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Log> getAllLogs() {
		Session session = sessionFactory.getCurrentSession();
		List<Log> logs = session.createQuery("from Log").getResultList();
		return logs;
	}

}
