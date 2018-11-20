package com.cs532.project2.srrest.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs532.project2.srrest.entity.PreKey;
import com.cs532.project2.srrest.entity.Prerequisite;


@Repository
public class PrerequisitesDAO implements PrerequisiteDAOI {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Prerequisite> getAllPrerequisites() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Prerequisite> tq = session.createNamedQuery("getPrerequisites", Prerequisite.class);
		List<Prerequisite> prerequisites = tq.getResultList();
		return prerequisites;
	}

	@Override
	public Prerequisite getPrerequisite(String dept, Integer cnum, String preDept, Integer preCnum) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Prerequisite.class, new PreKey(dept, cnum, preDept, preCnum));
	}

	@Override
	public void delete(String dept, Integer cnum, String preDept, Integer preCnum) {
		Session session = sessionFactory.getCurrentSession();
		Prerequisite p = session.get(Prerequisite.class, new PreKey(dept, cnum, preDept, preCnum));
		session.delete(p);
	}

	@Override
	public void save(Prerequisite p) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(p);
	}
}
