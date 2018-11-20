package com.cs532.project2.srrest.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name="enrollments")
@NamedNativeQuery(
		name = "getAllEnrollments",
		query = "{ ? = call proc.getAllEnrollments }",
		resultClass = Enrollment.class,
		hints = {
				@QueryHint(name = "org.hibernate.callable", value="true")
		}
)
public class Enrollment {

	@EmbeddedId
	private EnrollmentKey id;

	@Column(name="lgrade")
	private String lgrade;

	public EnrollmentKey getId() {
		return id;
	}

	public void setId(EnrollmentKey id) {
		this.id = id;
	}

	public String getLgrade() {
		return lgrade;
	}

	public void setLgrade(String lgrade) {
		this.lgrade = lgrade;
	}
}
