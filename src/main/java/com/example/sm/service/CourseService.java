package com.example.sm.service;

import java.util.ArrayList;
import java.util.List;

import com.example.sm.convertos.Convertor;
import com.example.sm.dao.CollegeRepo;
import com.example.sm.dto.CourseDTO;
import com.example.sm.model.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sm.dao.CourseRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Course;

@Service
public class CourseService implements ServiceInterface<CourseDTO>{

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private CollegeRepo collegeRepo;

	@Autowired
	private Convertor<Course, CourseDTO> convertor;
	
	@Override
	public List<CourseDTO> getAll() {

		return convertor.convertAllToDTO(courseRepo.findAll(), CourseDTO.class);
	}
	
	@Override
	public CourseDTO add(CourseDTO dto) {
		Course course = convertor.convertToEntity(new Course(), dto);
		courseRepo.save(course);
		return dto;
	}

	@Override
	public CourseDTO get(int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");

		return convertor.convertToDTO(course, new CourseDTO());
	}

	@Override
	public CourseDTO update(CourseDTO dto, int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");

		course = convertor.convertToEntity(course, dto);
		courseRepo.save(course);

		return dto;
	}

	@Override
	public void delete(int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");
		courseRepo.deleteById(Id);
	}

	public List<CourseDTO> getByCollege(int collegeId) {
		College college = collegeRepo.findById(collegeId).orElse(null);

		return convertor.convertAllToDTO(courseRepo.findByCollege(college), CourseDTO.class);
	}
}
