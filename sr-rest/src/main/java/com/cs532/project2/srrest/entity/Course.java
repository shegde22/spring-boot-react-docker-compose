package com.cs532.project2.srrest.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="courses")
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "getAllPrerequisites",
		query = "{ ? = call proc.getAllPrerequisites(?, ?) }",
		resultClass = Course.class,
		hints = {
				@QueryHint(name = "org.hibernate.callable", value="true")
		}
	),
	@NamedNativeQuery(
			name = "getAllCourses",
			query = "{ ? = call proc.getAllCourses }",
			resultClass = Course.class,
			hints = {
					@QueryHint(name = "org.hibernate.callable", value="true")
			}
	)}
)
public class Course implements Serializable{
	@EmbeddedId
	private CourseKey id;

	@NotEmpty
	@Column(name="title")
	private String title;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="course")
	@JsonIgnore
	private List<Cls> classes;

	public List<Cls> getClasses() {
		return classes;
	}

	public void setClasses(List<Cls> classes) {
		this.classes = classes;
	}

	public CourseKey getId() {
		return id;
	}

	public void setId(CourseKey id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
