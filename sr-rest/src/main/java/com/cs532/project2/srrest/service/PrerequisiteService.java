package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs532.project2.srrest.dao.PrerequisiteDAOI;
import com.cs532.project2.srrest.entity.Prerequisite;

@Service
@Transactional
public class PrerequisiteService implements PrerequisiteServiceI {

	@Autowired
	private PrerequisiteDAOI pDAO;

	@Override
	public List<Prerequisite> getAllPrerequisites() {
		return pDAO.getAllPrerequisites();
	}

	@Override
	public Prerequisite getPrerequisite(String dept, Integer cnum, String preDept, Integer preCnum) {
		return pDAO.getPrerequisite(dept, cnum, preDept, preCnum);
	}

	@Override
	public void delete(String dept, Integer cnum, String preDept, Integer preCnum) {
		pDAO.delete(dept, cnum, preDept, preCnum);
	}

	@Override
	public void save(Prerequisite p) {
		pDAO.save(p);
	}

}
