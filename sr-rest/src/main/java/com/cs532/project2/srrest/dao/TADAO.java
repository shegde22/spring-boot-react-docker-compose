package com.cs532.project2.srrest.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs532.project2.srrest.entity.TA;

@Repository
public class TADAO implements TADAOI {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<TA> getAllTAs() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<TA> tq = session.createNamedQuery("getAllTas", TA.class);
		List<TA> tas = tq.getResultList();
		return tas;
	}

	@Override
	public TA getTA(String bnum) {
		Session session = sessionFactory.getCurrentSession();
		TA ta = session.get(TA.class, bnum);
		return ta;
	}

	@Override
	public void delete(String bnum) {
		Session session = sessionFactory.getCurrentSession();
		TA ta = session.get(TA.class, bnum);
		session.delete(ta);
	}

	@Override
	public void save(TA ta, boolean isUpdate) {
		Session session = sessionFactory.getCurrentSession();
		if (isUpdate) {
			session.merge(ta);
		} else
			session.saveOrUpdate(ta);
	}
}
