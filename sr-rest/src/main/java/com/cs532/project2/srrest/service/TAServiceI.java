package com.cs532.project2.srrest.service;

import java.util.List;

import com.cs532.project2.srrest.entity.TA;

public interface TAServiceI {
	List<TA> getAllTAs();

	TA getTA(String bnum);

	void delete(String bnum);

	void save(TA ta);

	void update(TA taIn);
}
