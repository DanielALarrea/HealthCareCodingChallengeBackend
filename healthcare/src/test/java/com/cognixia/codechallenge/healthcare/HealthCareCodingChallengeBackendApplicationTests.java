package com.cognixia.codechallenge.healthcare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognixia.codechallenge.healthcare.controller.HealthCareController;
import com.cognixia.codechallenge.healthcare.model.Dependent;
import com.cognixia.codechallenge.healthcare.model.Enrollee;
import com.cognixia.codechallenge.healthcare.repository.DependentRepository;
import com.cognixia.codechallenge.healthcare.repository.EnrolleeRepository;
import com.cognixia.codechallenge.healthcare.utility.ErrorUtil;

@SpringBootTest
class HealthCareCodingChallengeBackendApplicationTests {

	// Controllers
	@Autowired
	private HealthCareController healthCareController;
	
	@Autowired
	private EnrolleeRepository enrolleeRepo;
	
	@Autowired
	private DependentRepository dependentRepo;
	
	@Test
	void testEnrolleeConstructorDefault() {
			new Enrollee();
	}
	
	@Test
	void testEnrolleeConstructorFull() {
		new Enrollee("Daniel", "123-456-7890", true, LocalDate.now());
	}
	
	@Test
	void testEnrolleeConstructorNoPhone() {
		new Enrollee("Daniel", false, LocalDate.now());
	}
	
	@Test
	void testGetEnrolleeById() {
		// Requires connection to database with enrollee with ID of 2
		healthCareController.getEnrolleeById(2);
	}
	
	@Test
	void testGetEnrolleeById_Fail_IdNotFound() {
		Enrollee controllerResponse = healthCareController.getEnrolleeById(-1).getBody();
		
		assertThat(controllerResponse.getEnrolleeName() == null);
	}
	
	@Test
	void testGetAllEnrollees() {
		healthCareController.getAllEnrollees();
	}
	
	@Test
	void testCreateNewEnrollee() {
		Optional<String> phoneNumber = Optional.of("123-456-7890");
		healthCareController.addNewEnrollee("Test", true, "2020-03-12", phoneNumber);
	}
	
	@Test
	void testCreateNewEnrollee_NoPhone() {
		Optional<String> phoneNumber = Optional.empty();
		healthCareController.addNewEnrollee("Test", true, "2020-03-12", phoneNumber);
	}
	
	@Test
	void testCreateNewEnrollee_Fail_BadDate() {
		Optional<String> phoneNumber = Optional.empty();
		
		String errorMessage = ErrorUtil.errorCreatingEnrollee() + ErrorUtil.errorBadDate();
		String controllerResponse = healthCareController.addNewEnrollee("Test", true, "wewe", phoneNumber).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateEnrolleeName() {
		healthCareController.updateEnrolleeName(2, "Updated");
	}
	
	@Test
	void testUpdateEnrolleeName_Fail_IdNotFound() {
		String errorMessage = ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateEnrolleeName(-1, "Failed to Update").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateEnrolleeStatus() {
		healthCareController.updateEnrolleeStatus(2, false);
	}
	
	@Test
	void testUpdateEnrolleeStatus_Fail_IdNotFound() {	
		String errorMessage = ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateEnrolleeStatus(-1, true).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateEnrolleePhoneNumber() {
		healthCareController.updateEnrolleePhoneNumber(2, "345-567-8901");
	}
	
	@Test
	void testUpdateEnrolleePhoneNumber_Fail_IdNotFound() {
		String errorMessage = ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateEnrolleePhoneNumber(-1, "345-567-8901").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateEnrolleeBirthDate() {
		healthCareController.updateEnrolleeBirthDate(2, "1997-05-12");
	}
	
	@Test
	void testUpdateEnrolleeBirthDate_Fail_IdNotFound() {
		String errorMessage = ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateEnrolleeBirthDate(-1, "1997-05-12").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateEnrolleeBirthDate_Fail_BadDate() {		
		String errorMessage = ErrorUtil.errorUpdatingEnrollee() + ErrorUtil.errorBadDate();
		String controllerResponse = healthCareController.updateEnrolleeBirthDate(2, "wewe").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testDeleteEnrollee() {
		healthCareController.addNewEnrollee("Test Delete", true, "2020-03-12", Optional.empty());
		
		healthCareController.deleteEnrollee(enrolleeRepo.findLargestEnrolleeId().getEnrolleeId());
	}
	
	@Test
	void testDeleteEnrollee_Fail_IdNotFound() {
		String errorMessage = ErrorUtil.errorDeletingEnrollee() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.deleteEnrollee(-1).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testGetDependentById() {
		// Requires connection to database with dependent with ID of 1
		healthCareController.getDependentById(1);
	}
	
	@Test
	void testGetDependentById_Fail() {		
		Dependent controllerResponse = healthCareController.getDependentById(-1).getBody();
		
		assertThat(controllerResponse.getDependentName() == null);
	}
	
	@Test
	void testGetAllDependents() {
		healthCareController.getAllDependents();
	}
	
	@Test
	void testGetAllDependentsByEnrolleeId() {
		healthCareController.getAllDependentsByEnrolleeId(2);
	}
	
	@Test
	void estGetAllDependentsByEnrolleeId_Fail_IdNotFound() {
		List<Dependent> dependents = healthCareController.getAllDependentsByEnrolleeId(-1);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			dependents.get(0);
		});
	}
	
	@Test
	void testCreateNewDependent() {
		healthCareController.addNewDependent("Test", "2020-03-12", 2);
	}
	
	@Test
	void testCreateNewDependent_Fail_IdNotFound() {		
		String errorMessage = ErrorUtil.errorCreatingDependent() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.addNewDependent("Test", "2020-03-12", -1).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testCreateNewDependent_Fail_BadDate() {
		String errorMessage = ErrorUtil.errorCreatingDependent() + ErrorUtil.errorBadDate();
		String controllerResponse = healthCareController.addNewDependent("Test", "wewe", 2).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateDependentName() {
		healthCareController.updateDependentName(1, "Updated");
	}
	
	@Test
	void testUpdateDependentName_Fail() {
		String errorMessage = ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateDependentName(-1, "Failed to Update").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateDependentBirthDate() {
		healthCareController.updateDependentBirthDate(1, "1997-05-12");
	}
	
	@Test
	void testUpdateDependentBirthDate_Fail_IdNotFound() {		
		String errorMessage = ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.updateDependentBirthDate(-1, "1997-05-12").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testUpdateDependentBirthDate_Fail_BadDate() {
		String errorMessage = ErrorUtil.errorUpdatingDependent() + ErrorUtil.errorBadDate();
		String controllerResponse = healthCareController.updateDependentBirthDate(1, "wewe").getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}
	
	@Test
	void testDeleteDependent() {
		healthCareController.addNewDependent("Test Delete", "2020-03-12", 2);
		
		healthCareController.deleteDependent(dependentRepo.findLargestDependentId().getDependentId());
	}
	
	@Test
	void testDeleteDependent_Fail_IdNotFound() {
		String errorMessage = ErrorUtil.errorDeletingDependent() + ErrorUtil.errorIdNotFound();
		String controllerResponse = healthCareController.deleteDependent(-1).getBody();
		
		assertThat(errorMessage.equals(controllerResponse));
	}

}
