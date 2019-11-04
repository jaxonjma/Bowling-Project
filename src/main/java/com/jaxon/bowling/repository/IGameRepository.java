package com.jaxon.bowling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jaxon.bowling.model.Game;

@Repository
public interface IGameRepository extends JpaRepository<Game, Long> {

	@Query("SELECT g FROM Game g WHERE g.process = :idProcess")
	List<Game> findGamesByProcess(@Param("idProcess") Long idProcess);
}
