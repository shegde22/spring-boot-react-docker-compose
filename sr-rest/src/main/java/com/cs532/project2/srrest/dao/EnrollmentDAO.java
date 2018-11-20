package com.cs532.project2.srrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Enrollment;
import com.cs532.project2.srrest.entity.EnrollmentKey;
import com.cs532.project2.srrest.util.ExceptionHelper;


@Repository
public class EnrollmentDAO implements EnrollmentDAOI {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Enrollment enrollStudent(Enrollment en, Model model) {
		Session session = sessionFactory.getCurrentSession();
		String bnum = en.getId().getBnum();
		String classid = en.getId().getClassid();
		StoredProcedureQuery q = session.createStoredProcedureCall("proc.enroll_student")
										.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
										.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
										.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
										.setParameter(1, bnum)
										.setParameter(2, classid);
		try {
			q.execute();
			Integer courses = (Integer) q.getOutputParameterValue(3);
			if (courses != null && courses == 4) {
				model.addAttribute("msg", "Student will be overloaded with the new enrollment");
			}
			model.addAttribute("success", true);
		} catch (PersistenceException ep) {
			model.addAttribute("success", false);
			try {
			 model.addAttribute("pserrors", ExceptionHelper.getRootCauseMessage(ep).split("\n")[0].split(":")[1].trim());
			} catch (ArrayIndexOutOfBoundsException e) {
				model.addAttribute("pserrors", "All conditions not satisfied, check logs for unsatisfied conditions");
			}
		} catch (Exception e) {
			model.addAttribute("success", false);
			model.addAttribute("pserrors", "Unkown error, check logs");
			e.printStackTrace();
		}
		return en;
	}

	@Override
	public List<Enrollment> allEnrollments() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Enrollment> q = session.createNamedQuery("getAllEnrollments", Enrollment.class);
		List<Enrollment> enrollments = q.getResultList();
		return enrollments;
	}

	@Override
	public void deleteEnrollment(String bnum, String classid, Model model) {
		Session session = sessionFactory.getCurrentSession();
		StoredProcedureQuery q = session.createStoredProcedureQuery("proc.drop_course")
										.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
										.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
										.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
										.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
										.setParameter(1, bnum)
										.setParameter(2, classid);
		try {
			q.execute();
			model.addAttribute("success", true);
			Integer studentsInClass = (Integer) q.getOutputParameterValue(3);
			Integer classesOfStudents = (Integer) q.getOutputParameterValue(4);
			String msg = "";
			if (studentsInClass != null && studentsInClass == 0)
				msg += "| The class now has no students |";
			if (classesOfStudents != null && classesOfStudents == 0)
				msg+="| The student is not enrolled in any classes |";
			model.addAttribute("msg", msg);
		} catch (PersistenceException ep) {
			model.addAttribute("success", false);
			try {
				model.addAttribute("pserrors", ExceptionHelper.getRootCauseMessage(ep).split("\n")[0].split(":")[1].trim());
			} catch (ArrayIndexOutOfBoundsException ea) {
				model.addAttribute("pserrors", "All conditions not satisfied, check logs for unsatisfied conditions");
			}
		} catch (Exception e) {
			model.addAttribute("success", false);
			model.addAttribute("pserrors", "Unkown error, check logs");
			e.printStackTrace();
		}
	}

	@Override
	public Enrollment getEnrollment(String bnum, String classid) {
		Session session = sessionFactory.getCurrentSession();
		Enrollment en = (Enrollment) session.get(Enrollment.class, new EnrollmentKey(bnum, classid));
		return en;
	}

	@Override
	public void save(@Valid Enrollment en, boolean isUpdate) {
		Session session = sessionFactory.getCurrentSession();
		if (isUpdate) {
			session.merge(en);
		} else
			session.saveOrUpdate(en);
	}
}
