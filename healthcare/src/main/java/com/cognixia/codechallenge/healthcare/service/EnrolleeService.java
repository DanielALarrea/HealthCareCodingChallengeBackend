package com.cognixia.codechallenge.healthcare.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.repository.EnrolleeRepository;

@Service
public class EnrolleeService {

	@Autowired
	private EnrolleeRepository enrolleeRepo;
	@Autowired
	private DependentService dependentService;
	
	// Create
	public boolean addNewEnrollee(String name, String phoneNumber, boolean activationStatus, String birthDate) {		
		Enrollee enrollee = new Enrollee(name, phoneNumber, activationStatus, LocalDate.parse(birthDate));
		enrolleeRepo.save(enrollee);
		
		return true;
	}
	
	public boolean addNewEnrollee(Enrollee enrollee) {
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
	
	public Enrollee getLatestEnrollee() {
		return enrolleeRepo.findLargestEnrolleeId();
	}
	
	public Integer getLatestEnrolleeId() {
		return enrolleeRepo.findLargestEnrolleeId().getEnrolleeId();
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
	
	public boolean updateEnrolleeBirthDate(Integer id, String birthDate) {
		Enrollee enrolleeToUpdate = getEnrolleeById(id);
		enrolleeToUpdate.setEnrolleeBirthDate(LocalDate.parse(birthDate));
		enrolleeRepo.save(enrolleeToUpdate);
		
		return true;
	}
	
	// Delete
	public boolean deleteEnrollee(Integer id) {
		List<Dependent> enrolleeDependents = dependentService.getAllDependentsByEnrollee(id);
		for(Dependent d: enrolleeDependents) {
			dependentService.deleteDependent(d.getDependentId());
		}
		enrolleeRepo.deleteById(id);
		return true;
	}
}
