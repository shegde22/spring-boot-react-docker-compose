package com.cs532.project2.srrest.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cs532.project2.srrest.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(
	name="students",
	uniqueConstraints= {@UniqueConstraint(columnNames= {"b#"}), @UniqueConstraint(columnNames= {"email"})})
@NamedNativeQueries({
	@NamedNativeQuery(
			name = "getAllStudents",
			query = "{ ? = call proc.getAllStudents }",
			resultClass = Student.class,
			hints = {
					@QueryHint(name = "org.hibernate.callable", value="true")
			}
	),
	@NamedNativeQuery(
			name = "getClassTa",
			query = "{ ? = call proc.getClassTa(:cid) }",
			resultClass = Student.class,
			hints = {
					@QueryHint(name = "org.hibernate.callable", value="true")
			}
	)
})

public class Student {
	@Id
	@Column(name="b#")
	@NotEmpty(message="is required")
	@Size(max=4, message="Max length 4")
	@Pattern(regexp="^B(.*)", message="required format Bxxxxxxxx")
	private String bnum;

	@NotEmpty(message="is required")
	@Column(name="first_name")
	private String fname;

	@NotEmpty(message="is required")
	@Column(name="last_name")
	private String lname;

	@NotEmpty(message="is required")
	@Column(name="email", unique=true)
	private String email;

	@Column(name="status")
	private String status;

	@NotEmpty(message="is required")
	@Column(name="deptname")
	private String deptname;

	@Max(4)
	@Min(0)
	private Double gpa;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="enrollments",
			joinColumns=@JoinColumn(name="b#"),
			inverseJoinColumns=@JoinColumn(name="classid")
			)
	@JsonIgnore
	private List<Cls> classes;

	public List<Cls> getClasses() {
		return classes;
	}

	public void setClasses(List<Cls> classes) {
		this.classes = classes;
	}

	@Column(name="bdate")
	private LocalDate bdate;

	public Student() {
	}

	public Student(@Pattern(regexp = "^B(.*)") String bnum, String fname, String lname, String email,
			String status, String deptname, @Max(4) @Min(0) double gpa, String bdate) {
		this.bnum = bnum;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.status = status;
		this.deptname = deptname;
		this.gpa = gpa;
		this.bdate = DateUtils.parseDate(bdate);
	}

	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public String getBdate() {
		return DateUtils.formatDate(bdate);
	}

	public void setBdate(String bdate) {
		this.bdate = DateUtils.parseDate(bdate);
	}

	@Override
	public String toString() {
		return "Student [bnum=" + bnum + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", status="
				+ status + ", deptname=" + deptname + ", gpa=" + gpa + ", bdate=" + bdate + "]";
	}
}
