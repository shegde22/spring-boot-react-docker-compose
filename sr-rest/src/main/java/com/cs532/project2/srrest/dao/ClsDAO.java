package com.cs532.project2.srrest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.query.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.Student;
import com.cs532.project2.srrest.util.ExceptionHelper;

@Repository
public class ClsDAO implements ClsDAOI {

	@Autowired
	private SessionFactory sessionFactoy;

	@Override
	public List<Cls> getClses() {
		Session session = sessionFactoy.getCurrentSession();
		TypedQuery<Cls> q = session.createNamedQuery("getAllClasses", Cls.class);
		List<Cls> classes = q.getResultList();
		return classes;
	}

	@Override
	public Cls getCls(String classid) {
		Session session = sessionFactoy.getCurrentSession();
		Cls cls = session.get(Cls.class, classid);
		return cls;
	}

	@Override
	public void deleteCls(String classid) {
		Session session = sessionFactoy.getCurrentSession();
		Query q = session.createQuery("delete from Cls where classid = :clsid");
		q.setParameter("clsid", classid);
		q.executeUpdate();
	}

	@Override
	public void saveCls(Cls cls, boolean isUpdate) {
		Session session = sessionFactoy.getCurrentSession();
		if (isUpdate) {
			Cls cur = session.get(Cls.class, cls.getClassid());
			Hibernate.initialize(cur.getStudents());
			Hibernate.initialize(cur.getCourse());
			cls.setStudents(cur.getStudents());
			cls.setCourse(cur.getCourse());
			session.merge(cls);
		}
		else
			session.saveOrUpdate(cls);
	}

	@Override
	public List<Student> getStudents(String clsid) {
		Session session = sessionFactoy.getCurrentSession();
		Cls cls = session.get(Cls.class, clsid);
		Hibernate.initialize(cls.getStudents());
		return cls == null ? new ArrayList<Student>() : cls.getStudents();
	}

	@Override
	public Cls getFullCls(String classid) {
		Session session = sessionFactoy.getCurrentSession();
		Cls cls = session.get(Cls.class, classid);
		if (cls != null)
			Hibernate.initialize(cls.getStudents());
		return cls == null ? null : cls;
	}

	@Override
	public Course getCourse(String classid) {
		Session session = sessionFactoy.getCurrentSession();
		Cls cls = session.get(Cls.class, classid);
		if (null != cls)
			Hibernate.initialize(cls.getCourse());
		return cls == null ? null : cls.getCourse();
	}

	@Override
	public Student getClassTA(String classid, Model model) {
		Session session = sessionFactoy.getCurrentSession();
		Student ta = null;
		try {
			TypedQuery<Student> tq = session.createNamedQuery("getClassTa", Student.class).setParameter("cid", classid);
			ta = tq.getSingleResult();
		} catch (PersistenceException ep) {
			model.addAttribute("success", false);
			try {
			 model.addAttribute("pserrors", ExceptionHelper.getRootCauseMessage(ep).split("\n")[0].split(":")[1].trim());
			} catch (ArrayIndexOutOfBoundsException e) {
				model.addAttribute("pserrors", "Invalid input");
			}
		} catch (Exception e) {
			model.addAttribute("success", false);
			model.addAttribute("pserrors", "Unkown error, check logs");
			e.printStackTrace();
		}
		return ta;
	}
}
