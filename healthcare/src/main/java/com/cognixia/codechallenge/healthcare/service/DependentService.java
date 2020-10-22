package com.cognixia.codechallenge.healthcare.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.repository.DependentRepository;

@Service
public class DependentService {
	
	@Autowired
	private DependentRepository dependentRepo;
	
	// Create
	public boolean addNewDependent(Enrollee enrollee, String name, String birthDate) {
		dependentRepo.save(new Dependent(name, LocalDate.parse(birthDate), enrollee));
		
		return true;
	}
	
	public boolean addNewDependent(Dependent dependent) {
		dependentRepo.save(dependent);
		
		return true;
	}
	
	// Read
	public Dependent getDependentById(Integer id) {
		return dependentRepo.findById(id).get();
	}
	
	public List<Dependent> getAllDependents() {
		return dependentRepo.findAll();
	}
	
	public List<Dependent> getAllDependentsByEnrollee(Integer enrolleeId) {
		return dependentRepo.findAllByEnrolleeEnrolleeId(enrolleeId);
		
	}
	
	public Dependent getLatestDependent() {
		return dependentRepo.findLargestDependentId();
	}
	
	public Integer getLatestDependentId() {
		return dependentRepo.findLargestDependentId().getDependentId();
	}
	
	// Update
	public boolean updateDependentName(Integer id, String name) {
		Dependent dependentToUpdate = getDependentById(id);
		dependentToUpdate.setDependentName(name);
		dependentRepo.save(dependentToUpdate);
		
		return true;
	}
	
	public boolean updateDependentBirthDate(Integer id, String birthDate) {
		Dependent dependentToUpdate = getDependentById(id);
		dependentToUpdate.setDependentBirthDate(LocalDate.parse(birthDate));
		dependentRepo.save(dependentToUpdate);
		
		return true;
	}
	
	// Delete
	public boolean deleteDependent(Integer id) {
		dependentRepo.deleteById(id);
		return true;
	}

}
