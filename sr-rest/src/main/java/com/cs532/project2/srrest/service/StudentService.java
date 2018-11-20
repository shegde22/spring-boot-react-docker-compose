package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.dao.StudentDAOI;
import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Student;


@Service
public class StudentService implements StudentServiceI {

	@Autowired
	private StudentDAOI studentDAO;

	@Override
	@Transactional
	public List<Student> getStudents() {
		return studentDAO.getStudents();
	}

	@Override
	@Transactional
	public void saveStudent(Student student) {
		studentDAO.saveStudent(student, false);
	}

	@Override
	@Transactional
	public void update(Student student) {
		studentDAO.saveStudent(student, true);
	}

	@Override
	@Transactional
	public Student getStudent(String bnum) {
		return studentDAO.getStudent(bnum);
	}
	
	@Override
	@Transactional
	public Student getStudentByEmail(String email) {
		return studentDAO.getStudentByEmail(email);
	}
	
	@Override
	@Transactional
	public void deleteStudent(String bnum, Model model) {
		studentDAO.deleteStudent(bnum, model);
	}

	@Override
	@Transactional
	public List<Cls> getClasses(String bnum) {
		return studentDAO.getClasses(bnum);
	}

}
