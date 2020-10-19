package com.cognixia.codechallenge.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.codechallenge.healthcare.model.Enrollee;

@Repository("enrolleeRepo")
public interface EnrolleeRepository extends JpaRepository<Enrollee, Integer>{
	@Query(value = "select * from enrollee order by enrollee_id desc limit 0,1", nativeQuery = true)
	public Enrollee findLargestEnrolleeId();
}
