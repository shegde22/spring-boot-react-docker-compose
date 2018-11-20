package com.cs532.project2.srrest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
public class EnrollmentKey implements Serializable{

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bnum == null) ? 0 : bnum.hashCode());
		result = prime * result + ((classid == null) ? 0 : classid.hashCode());
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
		EnrollmentKey other = (EnrollmentKey) obj;
		if (bnum == null) {
			if (other.bnum != null)
				return false;
		} else if (!bnum.equals(other.bnum))
			return false;
		if (classid == null) {
			if (other.classid != null)
				return false;
		} else if (!classid.equals(other.classid))
			return false;
		return true;
	}

	@NotEmpty(message="is required")
	@Column(name = "classid")
	@Size(max=5, message="max length 5")
	@Pattern(regexp="^c(.*)", message="required format cxxxx")
	private String classid;


	@Column(name="b#")
	@NotEmpty(message="is required")
	@Size(max=4, message="Max length 4")
	@Pattern(regexp="^B(.*)", message="required format Bxxxxxxxx")
	private String bnum;

	public EnrollmentKey() {}

	public EnrollmentKey(String bnum, String classid) {
		setBnum(bnum);
		setClassid(classid);
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}

}
