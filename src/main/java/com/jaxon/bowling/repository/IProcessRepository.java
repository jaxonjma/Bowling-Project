package com.jaxon.bowling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaxon.bowling.model.Process;

@Repository
public interface IProcessRepository extends JpaRepository<Process, Long> {

}
