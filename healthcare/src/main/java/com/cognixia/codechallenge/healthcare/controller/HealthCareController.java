package com.cognixia.codechallenge.healthcare.controller;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.codechallenge.healthcare.service.DependentService;
import com.cognixia.codechallenge.healthcare.service.EnrolleeService;

@RestController
public class HealthCareController {

	@Autowired
	private EnrolleeService enrolleeService;
	@Autowired
	private DependentService dependentService;
	
	@PostMapping("/enrollee/new")
	public ResponseEntity<String> addNewEnrollee(@RequestParam String name, 
												@RequestParam boolean activationStatus, 
												@RequestParam String birthDateString,
												@RequestParam Optional<String> phoneNumber) {
		LocalDate birthDate = LocalDate.parse(birthDateString);
		String phoneNumberOptional = "";
		try {
			phoneNumberOptional = phoneNumber.get();
		} catch (NoSuchElementException nsee) {
			System.out.println("No phone number, skipping");
			phoneNumberOptional = "";
		}
		if(enrolleeService.addNewEnrollee(name, phoneNumberOptional, activationStatus, birthDate)) {
			return new ResponseEntity<String>("Posted new enrollee", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Failed to post new enrollee", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/name")
	public ResponseEntity<String> updateEnrolleeName(@RequestParam Integer id, @RequestParam String name) {
		if(enrolleeService.updateEnrolleeName(id, name)) {
			return new ResponseEntity<String>("Updated enrollee name", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to update enrollee info", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/phonenumber")
	public ResponseEntity<String> updateEnrolleePhoneNumber(@RequestParam Integer id, @RequestParam String phoneNumber) {
		if(enrolleeService.updateEnrolleePhoneNumber(id, phoneNumber)) {
			return new ResponseEntity<String>("Updated enrollee phone number", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to update enrollee info", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/status")
	public ResponseEntity<String> updateEnrolleeStatus(@RequestParam Integer id, @RequestParam boolean status) {
		if(enrolleeService.updateEnrolleeStatus(id, status)) {
			return new ResponseEntity<String>("Updated enrollee status", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to update enrollee info", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollee/update/birthdate")
	public ResponseEntity<String> updateEnrolleeBirthDate(@RequestParam Integer id, @RequestParam String birthDate) {
		if(enrolleeService.updateEnrolleeBirthDate(id, birthDate)) {
			return new ResponseEntity<String>("Updated enrollee birth date", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to update enrollee info", HttpStatus.BAD_REQUEST);
		}
	}
	
	// Bugged, throws exception due to something with enrolleeId
	@PostMapping("/dependent/new")
	public ResponseEntity<String> addNewDependent(@RequestParam String name, 
												  @RequestParam String birthDateString,
												  @RequestParam Integer enrolleeId) {
		LocalDate birthDate = LocalDate.parse(birthDateString);
		if(dependentService.addNewDependent(enrolleeId, name, birthDate)) {
			return new ResponseEntity<String>("Posted new dependent", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Failed to post new dependent", HttpStatus.BAD_REQUEST);
		}
	}
}
