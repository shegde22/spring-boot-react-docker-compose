package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs532.project2.srrest.dao.LogDAOI;
import com.cs532.project2.srrest.dao.TADAOI;
import com.cs532.project2.srrest.entity.Log;
import com.cs532.project2.srrest.entity.TA;

@Service
public class LogService implements LogServiceI {

	@Autowired
	private LogDAOI lDAO;

	@Override
	@Transactional
	public List<Log> getAllLogs() {
		return lDAO.getAllLogs();
	}

}
