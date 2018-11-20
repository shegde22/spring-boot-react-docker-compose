package com.cs532.project2.srrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="tas")
@NamedNativeQuery(
		name = "getAllTas",
		query = "{ ? = call proc.getAllTas }",
		resultClass = TA.class,
		hints = {
				@QueryHint(name = "org.hibernate.callable", value="true")
		}
)
public class TA {
	@Id
	@Column(name="b#")
	@NotEmpty(message="is required")
	@Size(max=4, message="Max length 4")
	@Pattern(regexp="^B(.*)", message="required format Bxxxxxxxx")
	private String bnum;

	@Column(name="ta_level")
	private String taLevel;
	
	@Column(name="office")
	private String office;

	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}

	public String getTaLevel() {
		return taLevel;
	}

	public void setTaLevel(String taLevel) {
		this.taLevel = taLevel;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}
