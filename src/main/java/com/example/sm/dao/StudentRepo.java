package com.example.sm.dao;

import com.example.sm.model.College;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Student;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

	Student findByUsername(String username);

    List<Student> findByCollege(College college, Pageable pageable);
}
