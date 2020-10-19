package com.cognixia.codechallenge.healthcare.utility;

public class ErrorUtil {
	
	// Enrollee Error messages
	
	public static String errorCreatingEnrollee() {
		return "Failed to create enrollee";
	}
	
	public static String errorReadingEnrollee() {
		return "Failed to read enrollee info";
	}
	
	public static String errorUpdatingEnrollee() {
		return "Failed to update enrollee info";
	}
	
	public static String errorDeletingEnrollee() {
		return "Failed to delete enrollee";
	}
	
	// Dependent Error messages

	public static String errorCreatingDependent() {
		return "Failed to create dependent";
	}
	
	public static String errorReadingDependent() {
		return "Failed to read dependent info";
	}
	
	public static String errorUpdatingDependent() {
		return "Failed to update dependent info";
	}
	
	public static String errorDeletingDependent() {
		return "Failed to delete dependent";
	}
	
	// Extra clarification
	public static String errorIdNotFound() {
		return ", ID not found";
	}
	
	public static String errorBadDate() {
		return ", invalid date";
	}
}
