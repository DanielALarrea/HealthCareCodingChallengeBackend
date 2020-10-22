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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.repository.EnrolleeRepository;
import com.cognixia.codechallenge.healthcare.service.DependentService;
import com.cognixia.codechallenge.healthcare.service.EnrolleeService;
import com.cognixia.codechallenge.healthcare.utility.ErrorUtil;
import com.cognixia.codechallenge.healthcare.utility.SuccessUtil;

@RestController
@RequestMapping("/healthcare")
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
		} catch (NoSuchElementException | NumberFormatException ex) {
			return new ResponseEntity<Enrollee>(new Enrollee(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/all")
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}
	
	@GetMapping("/enrollees/{id}/name") 
	public ResponseEntity<String> getEnrolleeName(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(enrolleeService.getEnrolleeById(id).getEnrolleeName(), HttpStatus.OK);
		} catch (NoSuchElementException | NumberFormatException ex) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/{id}/phone-number") 
	public ResponseEntity<String> getEnrolleePhoneNumber(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(enrolleeService.getEnrolleeById(id).getPhoneNumber(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/{id}/status") 
	public ResponseEntity<String> getEnrolleeStatus(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(enrolleeService.getEnrolleeById(id).getActivationStatus().toString(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/{id}/birth-date") 
	public ResponseEntity<String> getEnrolleeBirthDate(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(enrolleeService.getEnrolleeById(id).getEnrolleeBirthDate().toString(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/enrollees")
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
			return new ResponseEntity<String>(SuccessUtil.createdEnrollee() + SuccessUtil.createdId(enrolleeService.getLatestEnrolleeId()), HttpStatus.CREATED);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingEnrollee() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/{id}/name")
	public ResponseEntity<String> updateEnrolleeName(@PathVariable Integer id, 
													 @RequestBody String name) {
		try {
			enrolleeService.updateEnrolleeName(id, name);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeName(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/{id}/phone-number")
	public ResponseEntity<String> updateEnrolleePhoneNumber(@PathVariable Integer id, 
															@RequestBody String phoneNumber) {
		try {
			enrolleeService.updateEnrolleePhoneNumber(id, phoneNumber);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleePhoneNumber(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/{id}/status")
	public ResponseEntity<String> updateEnrolleeStatus(@PathVariable Integer id, 
													   @RequestBody boolean status) {
		try {
			enrolleeService.updateEnrolleeStatus(id, status);
			return new ResponseEntity<String>(SuccessUtil.updatedEnrolleeStatus(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/enrollees/{id}/birth-date")
	public ResponseEntity<String> updateEnrolleeBirthDate(@PathVariable Integer id, 
														  @RequestBody String birthDate) {
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
	
	@DeleteMapping("/enrollees/{id}")
	public ResponseEntity<String> deleteEnrollee(@PathVariable Integer id) {
		try{
			enrolleeService.deleteEnrollee(id);
			return new ResponseEntity<String>(SuccessUtil.deletedEnrollee(), HttpStatus.OK);
		} catch (EmptyResultDataAccessException erdae) {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingEnrollee() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enrollees/{id}/dependents")
	public List<Dependent> getAllDependentsByEnrolleeId(@PathVariable Integer id) {
		try {
			return dependentService.getAllDependentsByEnrollee(id);
		} catch (NoSuchElementException | NumberFormatException ex) {
			return new ArrayList<Dependent>();
		}
	}

	// Dependent methods
	@GetMapping("/dependents/{id}")
	public ResponseEntity<Dependent> getDependentById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Dependent>(dependentService.getDependentById(id), HttpStatus.OK);
		} catch (NoSuchElementException | NumberFormatException ex) {
			return new ResponseEntity<Dependent>(new Dependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dependents/all")
	public List<Dependent> getAllDependents() {
		return dependentService.getAllDependents();
	}
	
	@GetMapping("/dependents/{id}/name") 
	public ResponseEntity<String> getDependentName(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(dependentService.getDependentById(id).getDependentName(), HttpStatus.OK);
		} catch (NoSuchElementException | NumberFormatException ex) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingDependent(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dependents/{id}/birth-date") 
	public ResponseEntity<String> getDependentBirthDate(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(dependentService.getDependentById(id).getDependentBirthDate().toString(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dependents/{id}/enrollee") 
	public ResponseEntity<String> getDependentEnrollee(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(dependentService.getDependentById(id).getEnrollee().toString(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dependents/{id}/enrollee-id") 
	public ResponseEntity<String> getDependentEnrolleeId(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(dependentService.getDependentById(id).getEnrollee().getEnrolleeId().toString(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorReadingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/dependents")
	public ResponseEntity<String> addNewDependent(@RequestParam String name, 
												  @RequestParam String birthDate,
												  @RequestParam Integer enrolleeId) {
		try {
			LocalDate.parse(birthDate);
			dependentService.addNewDependent(enrolleeService.getEnrolleeById(enrolleeId), name, birthDate);
			return new ResponseEntity<String>(SuccessUtil.createdDependent() + SuccessUtil.createdId(dependentService.getLatestDependentId()), HttpStatus.CREATED);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}  catch (DateTimeParseException dtpe) {
			return new ResponseEntity<String>(ErrorUtil.errorCreatingDependent() + ErrorUtil.errorBadDate(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/dependents/{id}/name")
	public ResponseEntity<String> updateDependentName(@PathVariable Integer id, 
													  @RequestBody String name) {
		try {
			dependentService.updateDependentName(id, name);
			return new ResponseEntity<String>(SuccessUtil.updatedDependentName(), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<String>(ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/dependents/{id}/birth-date")
	public ResponseEntity<String> updateDependentBirthDate(@PathVariable Integer id, 
			   											   @RequestBody String birthDate) {
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
	
	@DeleteMapping("/dependents/{id}")
	public ResponseEntity<String> deleteDependent(@PathVariable Integer id) {
		try {
			dependentService.deleteDependent(id);
			return new ResponseEntity<String>(SuccessUtil.deletedDependent(), HttpStatus.OK);
		} catch (EmptyResultDataAccessException erdae) {
			return new ResponseEntity<String>(ErrorUtil.errorDeletingDependent() + ErrorUtil.errorIdNotFound(), HttpStatus.BAD_REQUEST);
		}
	}
}
