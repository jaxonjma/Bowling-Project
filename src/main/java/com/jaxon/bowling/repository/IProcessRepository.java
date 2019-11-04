package com.jaxon.bowling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jaxon.bowling.model.Process;

@Repository
public interface IProcessRepository extends JpaRepository<Process, Long> {

	@Query("SELECT p FROM Process p WHERE p.completedAt IS NULL ORDER BY startDate ASC")
	List<Process> findAllPendingProcesses();
}
