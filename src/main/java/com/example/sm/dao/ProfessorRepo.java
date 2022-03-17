package com.example.sm.dao;

import com.example.sm.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Professor;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Integer>{

	Professor findByUsername(String username);
	List<Professor> findByCollege(College college, Pageable pageable);
}
