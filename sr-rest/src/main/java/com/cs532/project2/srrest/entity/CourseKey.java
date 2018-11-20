package com.cs532.project2.srrest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
public class CourseKey implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coursenum == null) ? 0 : coursenum.hashCode());
		result = prime * result + ((deptcode == null) ? 0 : deptcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseKey other = (CourseKey) obj;
		if (coursenum == null) {
			if (other.coursenum != null)
				return false;
		} else if (!coursenum.equals(other.coursenum))
			return false;
		if (deptcode == null) {
			if (other.deptcode != null)
				return false;
		} else if (!deptcode.equals(other.deptcode))
			return false;
		return true;
	}

	@Column(name="dept_code")
	private String deptcode;

	public CourseKey() {}

	public CourseKey(String deptcode, @Min(100) @Max(799) int coursenum) {
		super();
		this.deptcode = deptcode;
		this.coursenum = coursenum;
	}

	@Min(100)
	@Max(799)
	@Column(name="course#")
	private Integer coursenum;

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public Integer getCoursenum() {
		return coursenum;
	}

	public void setCoursenum(Integer coursenum) {
		this.coursenum = coursenum;
	}
}
