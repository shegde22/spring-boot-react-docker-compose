package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cs532.project2.srrest.dao.ClsDAOI;
import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.Student;


@Service
public class ClsService implements ClsServiceI {

	@Autowired
	private ClsDAOI clsDAO;

	@Override
	@Transactional
	public List<Cls> getClses() {
		return clsDAO.getClses();
	}

	@Override
	@Transactional
	public Cls getCls(String classid) {
		return clsDAO.getCls(classid);
	}

	@Override
	@Transactional
	public void deleteCls(String classid) {
		clsDAO.deleteCls(classid);
	}

	@Override
	@Transactional
	public void saveCls(@Valid Cls cls) {
		clsDAO.saveCls(cls, false);
	}

	@Override
	@Transactional
	public List<Student> getStudents(String cls) {
		return clsDAO.getStudents(cls);
	}

	@Override
	@Transactional
	public Course getCourse(String classid) {
		return clsDAO.getCourse(classid);
	}

	@Override
	@Transactional
	public Student getClassTA(String classid, Model model) {
		return clsDAO.getClassTA(classid, model);
	}

	@Override
	@Transactional
	public Cls getFullCls(String classid) {
		return clsDAO.getFullCls(classid);
	}

	@Override
	@Transactional
	public void update(Cls clsIn) {
		clsDAO.saveCls(clsIn, true);
		
	}
}
