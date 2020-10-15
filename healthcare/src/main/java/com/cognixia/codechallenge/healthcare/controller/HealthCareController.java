package com.cognixia.codechallenge.healthcare.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.service.DependentService;
import com.cognixia.codechallenge.healthcare.service.EnrolleeService;
import com.cognixia.codechallenge.healthcare.utility.ErrorUtil;
import com.cognixia.codechallenge.healthcare.utility.SuccessUtil;

@RestController
public class HealthCareController {

	@Autowired
	private EnrolleeService enrolleeService;
	@Autowired
	private DependentService dependentService;
	
	// Enrollee methods
	@GetMapping("/enrollee/{id}")
	public ResponseEntity<Enrollee> getEnrolleeById(@PathVariable Integer id) {
		return new ResponseEntity<Enrollee>(enrolleeService.getEnrolleeById(id), HttpStatus.OK);
	}
	
	@GetMapping("/enrollee/all")
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}
	
	@PostMapping("/enrollee/new")
	public ResponseEntity<String> addNewEnrollee(@RequestParam String name, 
												@RequestParam boolean activationStatus, 
												@RequestParam String birthDate,
												@RequestParam Optional<String> phoneNumber) {
		String phoneNumberOptional = "";
		try {
			phoneNumberOptional = phoneNumber.get();
		} catch (NoSuchElementException nsee) {
			System.out.println("No phone number, skipping");
			phoneNumberOptional = "null";
		}
		if(enrolleeService.addNewEnrollee(name, phoneNumberOptional, activationStatus, LocalDate.parse(birthDate))) {
			return new ResponseEntity<String>(SuccessUtil.createdEnrollee(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/name")
	public ResponseEntity<String> updateEnrolleeName(@RequestParam Integer id, @RequestParam String name) {
		if(enrolleeService.updateEnrolleeName(id, name)) {
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeName(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/phonenumber")
	public ResponseEntity<String> updateEnrolleePhoneNumber(@RequestParam Integer id, @RequestParam String phoneNumber) {
		if(enrolleeService.updateEnrolleePhoneNumber(id, phoneNumber)) {
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleePhoneNumber(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/status")
	public ResponseEntity<String> updateEnrolleeStatus(@RequestParam Integer id, @RequestParam boolean status) {
		if(enrolleeService.updateEnrolleeStatus(id, status)) {
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeStatus(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/birthdate")
	public ResponseEntity<String> updateEnrolleeBirthDate(@RequestParam Integer id, @RequestParam String birthDate) {
		if(enrolleeService.updateEnrolleeBirthDate(id, birthDate)) {
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeBirthDate(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/enrollee/delete/{id}")
	public ResponseEntity<String> deleteEnrollee(@PathVariable Integer id) {
		if(enrolleeService.deleteEnrollee(id)) {
			return new ResponseEntity<String>(SuccessUtil.deletedEnrollee(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingEnrollee(), HttpStatus.BAD_REQUEST);
		}
	}

	// Dependent methods
	@GetMapping("/dependent/{id}")
	public ResponseEntity<Dependent> getDependentById(@PathVariable Integer id) {
		return new ResponseEntity<Dependent>(dependentService.getDependentById(id), HttpStatus.OK);
	}
	
	@GetMapping("/dependent/all")
	public List<Dependent> getAllDependents() {
		return dependentService.getAllDependents();
	}
	
	@GetMapping("/dependent/all/{id}")
	public List<Dependent> getAllDependentsByEnrolleeId(@PathVariable Integer id) {
		return dependentService.getAllDependentsByEnrollee(id);
	}
	
	@PostMapping("/dependent/new")
	public ResponseEntity<String> addNewDependent(@RequestParam String name, 
												  @RequestParam String birthDate,
												  @RequestParam Integer enrolleeId) {
		if(dependentService.addNewDependent(enrolleeService.getEnrolleeById(enrolleeId), name, LocalDate.parse(birthDate))) {
			return new ResponseEntity<String>(SuccessUtil.createdDependent(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingDependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/dependent/update/name")
	public ResponseEntity<String> updateDependentName(@RequestParam Integer id, @RequestParam String name) {
		if(dependentService.updateDependentName(id, name)) {
			return new ResponseEntity<String>(SuccessUtil.updatedDependentName(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/dependent/update/birthdate")
	public ResponseEntity<String> updateDependentBirthDate(@RequestParam Integer id, @RequestParam String birthDate) {
		if(dependentService.updateDependentBirthDate(id, birthDate)) {
			return new ResponseEntity<String>(SuccessUtil.updatedDependentBirthDate(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/dependent/delete/{id}")
	public ResponseEntity<String> deleteDependent(@PathVariable Integer id) {
		if(dependentService.deleteDependent(id)) {
			return new ResponseEntity<String>(SuccessUtil.deletedDependent(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingDependent(), HttpStatus.BAD_REQUEST);
		}
	}
}
