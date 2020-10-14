package com.cognixia.codechallenge.healthcare.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "enrollee")
public class Enrollee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer enrolleeId;
	
	@Column(nullable = false)
	private String enrolleeName;
	
	@Column(nullable = true)
	private String phoneNumber;
	
	@Column(nullable = false)
	private Boolean activationStatus;
	
	@Column(nullable = false)
	private LocalDate enrolleeBirthDate;
	
	public Enrollee() {
		super();
	}

	public Enrollee(String enrolleeName, Boolean activationStatus, LocalDate enrolleeBirthDate) {
		super();
		this.enrolleeName = enrolleeName;
		this.phoneNumber = "Not given";
		this.activationStatus = activationStatus;
		this.enrolleeBirthDate = enrolleeBirthDate;
	}

	public Enrollee(String enrolleeName, String phoneNumber, Boolean activationStatus,
			LocalDate enrolleeBirthDate) {
		super();
		this.enrolleeName = enrolleeName;
		this.phoneNumber = phoneNumber;
		this.activationStatus = activationStatus;
		this.enrolleeBirthDate = enrolleeBirthDate;
	}

	public Integer getEnrolleeId() {
		return enrolleeId;
	}

	public void setEnrolleeId(Integer enrolleeId) {
		this.enrolleeId = enrolleeId;
	}

	public String getEnrolleeName() {
		return enrolleeName;
	}

	public void setEnrolleeName(String enrolleeName) {
		this.enrolleeName = enrolleeName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(Boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public LocalDate getEnrolleeBirthDate() {
		return enrolleeBirthDate;
	}

	public void setEnrolleeBirthDate(LocalDate enrolleeBirthDate) {
		this.enrolleeBirthDate = enrolleeBirthDate;
	}

	@Override
	public String toString() {
		return "Enrollee [enrolleeId=" + enrolleeId + ", enrolleeName=" + enrolleeName + ", phoneNumber=" + phoneNumber
				+ ", activationStatus=" + activationStatus + ", enrolleeBirthDate=" + enrolleeBirthDate + "]";
	}
}