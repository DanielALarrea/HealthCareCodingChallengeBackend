package com.cognixia.codechallenge.healthcare.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.repository.DependentRepository;

@Service
public class DependentService {
	
	@Autowired
	private DependentRepository dependentRepo;
	
	// Create
	public boolean addNewDependent(Integer enrolleeId, String name, LocalDate birthDate) {
		dependentRepo.save(new Dependent(name, birthDate, enrolleeId));
		
		return true;
	}

}
