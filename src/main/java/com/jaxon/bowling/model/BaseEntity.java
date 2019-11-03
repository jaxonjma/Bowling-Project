package com.jaxon.bowling.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class BaseEntity<T> {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_base")
	@SequenceGenerator(name="seq_base", initialValue=1, allocationSize=1)
	private T id;
	
	public T getId() {
		return id;
	}
	
	public void setId(T id) {
		this.id = id;
	}
	
	@Column(name = "ID_PROCESS")
	private Long process;
	
	public abstract String getUniqueCode();
	
	public Long getProcess() {
		return process;
	}
	
	public void setProcess(Long process) {
		this.process = process;
	}

}
