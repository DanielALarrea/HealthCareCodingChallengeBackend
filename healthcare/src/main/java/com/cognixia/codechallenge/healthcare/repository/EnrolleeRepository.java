package com.cognixia.codechallenge.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.codechallenge.healthcare.model.Enrollee;

@Repository("enrolleeRepo")
public interface EnrolleeRepository extends JpaRepository<Enrollee, Integer>{

}
