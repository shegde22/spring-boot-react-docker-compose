package com.cs532.project2.srrest.service;

import java.util.List;

import com.cs532.project2.srrest.entity.Prerequisite;

public interface PrerequisiteServiceI {

	public List<Prerequisite> getAllPrerequisites();

	public Prerequisite getPrerequisite(String dept, Integer cnum, String preDept, Integer preCnum);

	public void delete(String dept, Integer cnum, String preDept, Integer preCnum);

	void save(Prerequisite p);
}
