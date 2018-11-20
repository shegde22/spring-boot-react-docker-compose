package com.cs532.project2.srrest.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name="prerequisites")
@NamedNativeQuery(
		name = "getPrerequisites",
		query = "{ ? = call proc.getPrerequisites }",
		resultClass = Prerequisite.class,
		hints = {
				@QueryHint(name = "org.hibernate.callable", value="true")
		}
)
public class Prerequisite {

	@EmbeddedId
	private PreKey id;

	public PreKey getId() {
		return id;
	}

	public void setId(PreKey id) {
		this.id = id;
	}
}
