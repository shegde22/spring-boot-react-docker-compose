package com.cs532.project2.srrest.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Enrollment;

public interface EnrollmentDAOI {
	Enrollment enrollStudent(Enrollment en, Model model);
	void deleteEnrollment(String bnum, String classid, Model model);
	List<Enrollment> allEnrollments();
	Enrollment getEnrollment(String bnum, String classid);
	void save(@Valid Enrollment en, boolean isUpdate);
}
