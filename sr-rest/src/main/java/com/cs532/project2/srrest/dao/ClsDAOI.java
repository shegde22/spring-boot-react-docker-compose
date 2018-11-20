package com.cs532.project2.srrest.dao;

import java.util.List;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.Student;


public interface ClsDAOI {
	List<Cls> getClses();
	Cls getCls(String classid);
	void deleteCls(String classid);
	void saveCls(Cls cls, boolean isUpdate);
	List<Student> getStudents(String cls);
	Course getCourse(String classid);
	Student getClassTA(String classid, Model model);
	Cls getFullCls(String classid);
}
