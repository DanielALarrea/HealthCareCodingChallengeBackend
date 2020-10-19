package com.cognixia.codechallenge.healthcare.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping("/enrollees/{id}")
	public ResponseEntity<Enrollee> getEnrolleeById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Enrollee>(enrolleeService.getEnrolleeById(id), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Enrollee>(new Enrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/all")
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}
	
	@PostMapping("/enrollees/new")
	public ResponseEntity<String> addNewEnrollee(@RequestParam String name, 
												@RequestParam boolean activationStatus, 
												@RequestParam String birthDate,
												@RequestParam Optional<String> phoneNumber) {
		String phoneNumberOptional = "";
		try {
			phoneNumberOptional = phoneNumber.get();
		} catch (NoSuchElementException nsee) {
			System.out.println("No phone number given");
			phoneNumberOptional = "Not given";
		}
		
		try {
			LocalDate.parse(birthDate);
			enrolleeService.addNewEnrollee(name, phoneNumberOptional, activationStatus, birthDate);
			return new ResponseEntity<String>(SuccessUtil.createdEnrollee(), HttpStatus.CREATED);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingEnrollee() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/update/name")
	public ResponseEntity<String> updateEnrolleeName(@RequestParam Integer id, 
													 @RequestParam String name) {
		try {
			enrolleeService.updateEnrolleeName(id, name);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeName(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/update/phonenumber")
	public ResponseEntity<String> updateEnrolleePhoneNumber(@RequestParam Integer id, 
															@RequestParam String phoneNumber) {
		try {
			enrolleeService.updateEnrolleePhoneNumber(id, phoneNumber);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleePhoneNumber(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/update/status")
	public ResponseEntity<String> updateEnrolleeStatus(@RequestParam Integer id, 
													   @RequestParam boolean status) {
		try {
			enrolleeService.updateEnrolleeStatus(id, status);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeStatus(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/update/birthdate")
	public ResponseEntity<String> updateEnrolleeBirthDate(@RequestParam Integer id, 
														  @RequestParam String birthDate) {
		try {
			LocalDate.parse(birthDate);
			enrolleeService.updateEnrolleeBirthDate(id, birthDate);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeBirthDate(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/enrollees/delete")
	public ResponseEntity<String> deleteEnrollee(@RequestParam Integer id) {
		try{
			enrolleeService.deleteEnrollee(id);
			return new ResponseEntity<String>(SuccessUtil.deletedEnrollee(), HttpStatus.OK);
		} catch (EmptyResultDataAccessException erdae) {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}

	// Dependent methods
	@GetMapping("/dependents/{id}")
	public ResponseEntity<Dependent> getDependentById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Dependent>(dependentService.getDependentById(id), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Dependent>(new Dependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dependents/all")
	public List<Dependent> getAllDependents() {
		return dependentService.getAllDependents();
	}
	
	@GetMapping("/dependents/all/{id}")
	public List<Dependent> getAllDependentsByEnrolleeId(@PathVariable Integer id) {
		try {
			return dependentService.getAllDependentsByEnrollee(id);
		} catch (NoSuchElementException nsee) {
			return new ArrayList<Dependent>();
		}
	}
	
	@PostMapping("/dependents/new")
	public ResponseEntity<String> addNewDependent(@RequestParam String name, 
												  @RequestParam String birthDate,
												  @RequestParam Integer enrolleeId) {
		try {
			LocalDate.parse(birthDate);
			dependentService.addNewDependent(enrolleeService.getEnrolleeById(enrolleeId), name, birthDate);
			return new ResponseEntity<String>(SuccessUtil.createdDependent(), HttpStatus.CREATED);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}  catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingDependent() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/dependents/update/name")
	public ResponseEntity<String> updateDependentName(@RequestParam Integer id, 
													  @RequestParam String name) {
		try {
			dependentService.updateDependentName(id, name);
			return new ResponseEntity<String>(SuccessUtil.updatedDependentName(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/dependents/update/birthdate")
	public ResponseEntity<String> updateDependentBirthDate(@RequestParam Integer id, 
			   											   @RequestParam String birthDate) {
		try {
			LocalDate.parse(birthDate);
			dependentService.updateDependentBirthDate(id, birthDate);
			return new ResponseEntity<String>(SuccessUtil.updatedDependentBirthDate(), HttpStatus.CREATED);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}  catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/dependents/delete")
	public ResponseEntity<String> deleteDependent(@RequestParam Integer id) {
		try {
			dependentService.deleteDependent(id);
			return new ResponseEntity<String>(SuccessUtil.deletedDependent(), HttpStatus.OK);
		} catch (EmptyResultDataAccessException erdae) {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
}
