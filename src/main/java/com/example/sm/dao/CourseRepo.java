package com.example.sm.dao;

import com.example.sm.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Course;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer>{

    List<Course> findByCollege(College college);
}
