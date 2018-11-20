package com.cs532.project2.srrest.dao;

import java.util.List;

import com.cs532.project2.srrest.entity.TA;

public interface TADAOI {
	List<TA> getAllTAs();

	TA getTA(String bnum);

	void delete(String bnum);

	void save(TA ta, boolean isUpdate);
}
