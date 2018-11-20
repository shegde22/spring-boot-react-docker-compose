package com.cs532.project2.srrest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Student;
import com.cs532.project2.srrest.util.ExceptionHelper;


@Repository
public class StudentDAO implements StudentDAOI {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Student> getStudents() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Student> q = session.createNamedQuery("getAllStudents", Student.class);
		List<Student> students = q.getResultList();
		return students;
	}

	@Override
	public Student getStudentByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<Student> q = session.createQuery("from Student where email = :email").setParameter("email", email);
		Student student = null;
		try {
			student = q.getSingleResult();
		} catch (NoResultException e ) {
			return null;
		}
		return student;
	}

	@Override
	public void saveStudent(Student student, boolean isUpdate) {
		Session session = sessionFactory.getCurrentSession();
		if (isUpdate) {
			Student cur = session.get(Student.class, student.getBnum());
			Hibernate.initialize(cur.getClasses());
			student.setClasses(cur.getClasses());
			session.merge(student);
		} else {
			session.saveOrUpdate(student);
		}
	}

	@Override
	public Student getStudent(String bnum) {
		Session session = sessionFactory.getCurrentSession();
		return (Student) session.get(Student.class, bnum);
	}

	@Override
	public void deleteStudent(String bnum, Model model) {
		Session session = sessionFactory.getCurrentSession();
		StoredProcedureQuery sq = session.createStoredProcedureQuery("proc.drop_student")
										 .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
										 .setParameter(1, bnum);
		try {
			sq.execute();
		} catch (PersistenceException ep) {
			try {
			 model.addAttribute("pserrors", ExceptionHelper.getRootCauseMessage(ep).split("\n")[0].split(":")[1].trim());
			} catch (ArrayIndexOutOfBoundsException e) {
				model.addAttribute("pserrors", "Could not delete student");
			}
		} catch (Exception e) {
			model.addAttribute("pserrors", "Unkown error, check logs");
			e.printStackTrace();
		}
	}

	@Override
	public List<Cls> getClasses(String bnum) {
		Session session = sessionFactory.getCurrentSession();
		Student cur = (Student) session.get(Student.class, bnum);
		Hibernate.initialize(cur.getClasses());
		return cur.getClasses() == null ? new ArrayList<Cls>() : cur.getClasses();
	}

}
