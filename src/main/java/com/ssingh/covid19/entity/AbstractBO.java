package com.ssingh.covid19.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractBO {
	@Id
	long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
