package com.cs532.project2.srrest.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
	name="classes",
	uniqueConstraints={ 
		@UniqueConstraint(
				columnNames= { "dept_code", "course#", "sect#", "year", "semester" }
			)
		}
	)
@NamedNativeQuery(
		name = "getAllClasses",
		query = "{ ? = call proc.getAllClasses }",
		resultClass = Cls.class,
		hints = {
				@QueryHint(name = "org.hibernate.callable", value="true")
		}
)
public class Cls implements Serializable {
	@Id
	@NotEmpty(message="is required")
	@Column(name = "classid")
	@Size(max=5, message="max length 5")
	@Pattern(regexp="^c(.*)", message="required format cxxxx")
	private String classid;

	@NotEmpty(message="is required")
	@Column(name="dept_code")
	private String deptcode;

	@Min(100)
	@Max(799)
	@NotNull(message="is required")
	@Column(name="course#")
	private Integer coursenum;

	@Size(max=8, message="8 char max")
	@Column(name = "semester")
	private String semester;

	@Size(max=10,message="10 char max")
	@Column(name = "room")
	private String room;

	@Column(name = "year")
	private Integer year;

	@Min(1)
	@Max(99)
	@Column(name = "sect#")
	private Integer sect;

	@Max(999)
	@Column(name = "limit")
	private Integer limit;

	@Column(name = "class_size")
	private Integer classSize;

	@Size(max=4, message="4 char max")
	@Column(name = "ta_B#")
	private String taBnum;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="dept_code", referencedColumnName="dept_code", insertable=false, updatable=false),
		@JoinColumn(name="course#", referencedColumnName="course#", insertable=false, updatable=false)
	})
	@JsonIgnore
	private Course course;

	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="enrollments",
			joinColumns=@JoinColumn(name="classid"),
			inverseJoinColumns=@JoinColumn(name="b#")
			)
	@JsonIgnore
	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSect() {
		return sect;
	}

	public void setSect(Integer sect) {
		this.sect = sect;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getClassSize() {
		return classSize;
	}

	public void setClassSize(Integer classSize) {
		this.classSize = classSize;
	}

	public String getTaBnum() {
		return taBnum;
	}

	public void setTaBnum(String taBnum) {
		this.taBnum = taBnum;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	
}
