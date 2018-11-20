package com.cs532.project2.srrest.dao;

import java.util.List;

import com.cs532.project2.srrest.entity.Prerequisite;

public interface PrerequisiteDAOI {
	List<Prerequisite> getAllPrerequisites();

	Prerequisite getPrerequisite(String dept, Integer cnum, String preDept, Integer preCnum);

	void delete(String dept, Integer cnum, String preDept, Integer preCnum);

	void save(Prerequisite p);
}
