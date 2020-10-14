package com.cognixia.codechallenge.healthcare.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.repository.EnrolleeRepository;

@Service
public class EnrolleeService {

	@Autowired
	private EnrolleeRepository enrolleeRepo;
	
	// Create
	public boolean addNewEnrollee(String name, String phoneNumber, boolean activationStatus, LocalDate birthDate) {
		String phoneNumToPass = "";
		if(phoneNumber == "") {
			phoneNumToPass = "Not given";
		} else {
			phoneNumToPass = phoneNumber;
		}
		
		Enrollee enrollee = new Enrollee(name, phoneNumToPass, activationStatus, birthDate);
		enrolleeRepo.save(enrollee);
		
		return true;
	}
	
	// Read
	public Enrollee getEnrolleeById(Integer id) {
		return enrolleeRepo.findById(id).get();
	}
	
	public List<Enrollee> getAllEnrollees() {
		return enrolleeRepo.findAll();
	}
	
	// Update
	public boolean updateEnrolleeName(Integer id, String newName) {
		Enrollee enrolleeToUpdate = getEnrolleeById(id);
		enrolleeToUpdate.setEnrolleeName(newName);
		enrolleeRepo.save(enrolleeToUpdate);
		
		return true;
	}
	
	public boolean updateEnrolleePhoneNumber(Integer id, String phoneNumber) {
		Enrollee enrolleeToUpdate = getEnrolleeById(id);
		enrolleeToUpdate.setPhoneNumber(phoneNumber);
		enrolleeRepo.save(enrolleeToUpdate);
		
		return true;
	}
	
	public boolean updateEnrolleeStatus(Integer id, boolean status) {
		Enrollee enrolleeToUpdate = getEnrolleeById(id);
		enrolleeToUpdate.setActivationStatus(status);
		enrolleeRepo.save(enrolleeToUpdate);
		
		return true;
	}
	
	public boolean updateEnrolleeBirthDate(Integer id, String birthDateString) {
		Enrollee enrolleeToUpdate = getEnrolleeById(id);
		enrolleeToUpdate.setEnrolleeBirthDate(LocalDate.parse(birthDateString));
		enrolleeRepo.save(enrolleeToUpdate);
		
		return true;
	}
	
	// Delete
	public boolean deleteEnrollee(Integer id) {
		enrolleeRepo.deleteById(id);
		// TODO: Logic for deleting dependents tied to enrollee
		// Need to make logic for tying dependents to enrollees - in dependent service?
		return true;
	}
}
