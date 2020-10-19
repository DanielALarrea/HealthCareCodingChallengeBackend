package com.cognixia.codechallenge.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.codechallenge.healthcare.model.Dependent;

@Repository("dependentRepo")
public interface DependentRepository extends JpaRepository<Dependent, Integer>{
	List<Dependent> findAllByEnrolleeEnrolleeId(Integer enrollee_id);
	
	@Query(value = "select * from dependent order by dependent_id desc limit 0,1", nativeQuery = true)
	public Dependent findLargestDependentId();
}
