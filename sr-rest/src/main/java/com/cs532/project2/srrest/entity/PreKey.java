package com.cs532.project2.srrest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreKey implements Serializable {
	@Column(name="dept_code")
	private String dept;
	@Column(name="course#")
	private Integer cnum;
	@Column(name="pre_dept_code")
	private String preDept;
	@Column(name="pre_course#")
	private Integer preCnum;
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Integer getCnum() {
		return cnum;
	}
	public void setCnum(Integer cnum) {
		this.cnum = cnum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnum == null) ? 0 : cnum.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((preCnum == null) ? 0 : preCnum.hashCode());
		result = prime * result + ((preDept == null) ? 0 : preDept.hashCode());
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
		PreKey other = (PreKey) obj;
		if (cnum == null) {
			if (other.cnum != null)
				return false;
		} else if (!cnum.equals(other.cnum))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (preCnum == null) {
			if (other.preCnum != null)
				return false;
		} else if (!preCnum.equals(other.preCnum))
			return false;
		if (preDept == null) {
			if (other.preDept != null)
				return false;
		} else if (!preDept.equals(other.preDept))
			return false;
		return true;
	}
	public String getPreDept() {
		return preDept;
	}
	public void setPreDept(String preDept) {
		this.preDept = preDept;
	}
	public Integer getPreCnum() {
		return preCnum;
	}
	public void setPreCnum(Integer preCnum) {
		this.preCnum = preCnum;
	}
	public PreKey() {}
	public PreKey(String dept, Integer cnum, String preDept, Integer preCnum) {
		super();
		this.dept = dept;
		this.cnum = cnum;
		this.preDept = preDept;
		this.preCnum = preCnum;
	}
	
}
