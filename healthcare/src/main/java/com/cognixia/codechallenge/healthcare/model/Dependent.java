package com.cognixia.codechallenge.healthcare.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private Enrollee enrollee;

	public Dependent() {
		super();
	}

	public Dependent(String dependentName, LocalDate dependentBirthDate, Enrollee enrollee) {
		super();
		this.dependentName = dependentName;
		this.dependentBirthDate = dependentBirthDate;
		this.enrollee = enrollee;
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

	public Enrollee getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Enrollee enrollee) {
		this.enrollee = enrollee;
	}

	@Override
	public String toString() {
		return "Dependent [dependentId=" + dependentId + ", dependentName=" + dependentName + ", dependentBirthDate="
				+ dependentBirthDate + ", enrollee=" + enrollee + "]";
	}

}