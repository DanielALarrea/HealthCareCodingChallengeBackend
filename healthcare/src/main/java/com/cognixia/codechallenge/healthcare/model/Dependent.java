package com.cognixia.codechallenge.healthcare.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "dependent")
public class Dependent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer dependentId;
	
	@Column(nullable = false)
	private String dependentName;
	
	@Column(nullable = false)
	private LocalDate dependentBirthDate;
	
	@ManyToOne(targetEntity = Enrollee.class)
	@JoinColumn(name = "enrollee_id")
	private Integer enrolleeId;

	public Dependent() {
		super();
	}

	public Dependent(String dependentName, LocalDate dependentBirthDate, Integer enrolleeId) {
		super();
		this.dependentName = dependentName;
		this.dependentBirthDate = dependentBirthDate;
		this.enrolleeId = enrolleeId;
	}

	public Integer getDependentId() {
		return dependentId;
	}

	public void setDependentId(Integer dependentId) {
		this.dependentId = dependentId;
	}

	public String getDependentName() {
		return dependentName;
	}

	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}

	public LocalDate getDependentBirthDate() {
		return dependentBirthDate;
	}

	public void setDependentBirthDate(LocalDate dependentBirthDate) {
		this.dependentBirthDate = dependentBirthDate;
	}

	public Integer getEnrolleeId() {
		return enrolleeId;
	}

	public void setEnrolleeId(Integer enrolleeId) {
		this.enrolleeId = enrolleeId;
	}

}