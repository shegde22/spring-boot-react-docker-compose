package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs532.project2.srrest.dao.TADAOI;
import com.cs532.project2.srrest.entity.TA;

@Service
@Transactional
public class TAService implements TAServiceI {

	@Autowired
	private TADAOI taDAO;

	@Override
	public List<TA> getAllTAs() {
		return taDAO.getAllTAs();
	}

	@Override
	public TA getTA(String bnum) {
		return taDAO.getTA(bnum);
	}

	@Override
	public void delete(String bnum) {
		taDAO.delete(bnum);
	}

	@Override
	public void save(TA ta) {
		taDAO.save(ta, false);
	}

	@Override
	public void update(TA taIn) {
		taDAO.save(taIn, true);
		
	}

}
