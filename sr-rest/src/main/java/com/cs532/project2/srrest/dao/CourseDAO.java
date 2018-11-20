package com.cs532.project2.srrest.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.hibernate.query.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.CourseKey;


@Repository
public class CourseDAO implements CourseDAOI {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Course> getCourses() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Course> q = session.createNamedQuery("getAllCourses", Course.class);
		List<Course> courses = q.getResultList();
		return courses;
	}

	@Override
	public List<Cls> getClasses(String dept, Integer coursenum) {
		Session session = sessionFactory.getCurrentSession();
		Serializable id =  new CourseKey(dept, coursenum);
		Course course = session.get(Course.class, id);
		Hibernate.initialize(course.getClasses());
		return course.getClasses();
	}
	
	@Override
	public Course getCourse(String dept, int coursenum) {
		Session session = sessionFactory.getCurrentSession();
		Serializable id =  new CourseKey(dept, coursenum);
		Course course = session.get(Course.class, id);
		return course;
	}

	@Override
	public void deleteCourse(String dept, int coursenum) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete from Course where id.deptcode = :dept and id.coursenum = :num");
		q.setParameter("dept", dept);
		q.setParameter("num", coursenum);
		q.executeUpdate();
	}

	@Override
	public void saveCourse(Course course, boolean isUpdate) {
		Session session = sessionFactory.getCurrentSession();
		if (isUpdate) {
			Course cur = session.get(Course.class, course.getId());
			Hibernate.initialize(cur.getClasses());
			course.setClasses(cur.getClasses());
			session.merge(course);
		} else {
			session.saveOrUpdate(course);
		}
	}

	@Override
	public List<Course> getAllPrerequisites(String dept, int coursenum) {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Course> q = session.createNamedQuery("getAllPrerequisites", Course.class);
		q.setParameter(1, dept);
		q.setParameter(2, coursenum);
		List<Course> prerequisites = q.getResultList();
		return prerequisites;
	}
}
