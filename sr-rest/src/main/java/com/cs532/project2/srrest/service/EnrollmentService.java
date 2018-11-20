package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.dao.EnrollmentDAOI;
import com.cs532.project2.srrest.entity.Enrollment;


@Service
public class EnrollmentService implements EnrollmentServiceI {

	@Autowired
	private EnrollmentDAOI eDAO;

	@Override
	@Transactional
	public Enrollment enrollStudent(Enrollment en, Model model) {
		return eDAO.enrollStudent(en, model);
	}

	@Override
	@Transactional
	public List<Enrollment> allEnrollments() {
		return eDAO.allEnrollments();
	}

	@Override
	@Transactional
	public void deleteEnrollment(String bnum, String classid, Model model) {
		eDAO.deleteEnrollment(bnum, classid, model);
	}

	@Override
	@Transactional
	public Enrollment getEnrollment(String bnum, String classid) {
		return eDAO.getEnrollment(bnum, classid);
	}

	@Override
	@Transactional
	public void saveCourse(@Valid Enrollment en) {
		eDAO.save(en, false);
	}

	@Override
	public void update(@Valid Enrollment eIn) {
		eDAO.save(eIn, true);
		
	}

}
