package com.cognixia.codechallenge.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.codechallenge.healthcare.model.Dependent;

@Repository("dependentRepo")
public interface DependentRepository extends JpaRepository<Dependent, Integer>{

}
