package com.example.sm.dao;

import com.example.sm.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepo extends JpaRepository<College, Integer> {

}
