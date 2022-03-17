package com.example.sm.service;

import java.util.ArrayList;
import java.util.List;

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
	CourseRepo courseRepo;

	@Autowired
	CollegeRepo collegeRepo;
	
	@Override
	public List<CourseDTO> getAll() {

		List<CourseDTO> dtos = new ArrayList<>();
		List<Course> courses = courseRepo.findAll();

		for (Course course : courses) {
			CourseDTO dto = convertToDTO(course);
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public CourseDTO add(CourseDTO dto) {
		Course course = convertToCourse(dto);
		courseRepo.save(course);
		return dto;
	}

	@Override
	public CourseDTO get(int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");

		return convertToDTO(course);
	}

	@Override
	public CourseDTO update(CourseDTO dto, int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");

		course = convertToCourse(dto);
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

		List<CourseDTO> dtos = new ArrayList<>();
		List<Course> courses = courseRepo.findByCollege(college);

		for (Course course : courses) {
			CourseDTO dto = convertToDTO(course);
			dtos.add(dto);
		}

		return dtos;
	}

	private CourseDTO convertToDTO(Course course) {
		CourseDTO dto = new CourseDTO();
		dto.setCollege(course.getCollege());
		dto.setId(course.getId());
		dto.setName(course.getName());
		dto.setProfessor(course.getProfessor());
		dto.setUnit(course.getUnit());

		return dto;
	}

	private Course convertToCourse(CourseDTO dto) {
		Course course = new Course();

		course.setCollege(dto.getCollege());
		course.setName(dto.getName());
		course.setProfessor(dto.getProfessor());
		course.setUnit(dto.getUnit());

		return course;
	}
}
