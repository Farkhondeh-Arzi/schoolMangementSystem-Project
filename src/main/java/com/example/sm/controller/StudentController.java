package com.example.sm.controller;

import com.example.sm.dto.StudentDTO;
import com.example.sm.model.StudentCourse;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;
import com.example.sm.service.StudentCourseService;
import com.example.sm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	StudentCourseService studentCourseService;
	
	@Autowired
	CollegeService collegeService;
	
	@GetMapping("/colleges/{collegeId}/students")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CollectionModel<StudentDTO> getAll(@PathVariable int collegeId,
										   @RequestParam(defaultValue = "0") Integer pageNo,
										   @RequestParam(defaultValue = "10") Integer pageSize) {
		
		List<StudentDTO> students = studentService.getByCollege(collegeId, pageNo, pageSize);
		
		for (StudentDTO student: students) {
			Link selfLink =  WebMvcLinkBuilder.linkTo(StudentController.class).slash("student")
					.slash(student.getId()).withSelfRel();
			student.add(selfLink);
			
			Link courseLink =  WebMvcLinkBuilder.linkTo(StudentController.class)
					.slash("student").slash(student.getId()).slash("addCourse").withRel("course");
			student.add(courseLink);
			
			Link averageLink =  WebMvcLinkBuilder.linkTo(StudentController.class).
					slash("student").slash(student.getId()).slash("average").withRel("average");
			student.add(averageLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(StudentController.class).slash("colleges")
				.slash(collegeId).slash("students").withSelfRel();
		return CollectionModel.of(students, link);
	}
	
	@PostMapping("/colleges/{collegeId}/students")
	@PreAuthorize("hasAuthority('ADMIN')")
	public StudentDTO addToStudents(@Valid @RequestBody StudentDTO studentDTO) {
		
		//the repository sets Id after adding it

		return studentService.add(studentDTO);
	}
	
	@PutMapping("/colleges/{collegeId}/{studentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public StudentDTO updateStudent(@Valid @RequestBody StudentDTO studentDTO, @PathVariable int studentId) {
		return studentService.update(studentDTO, studentId);
	}
	
	@DeleteMapping("/colleges/{collegeId}/{studentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteStudent(@PathVariable int studentId) {
		studentService.delete(studentId);
	}
	
	@GetMapping("student/{studentId}")
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public StudentDTO getStudent(@PathVariable int studentId) {
		StudentDTO student = studentService.get(studentId);
		Link courseLink =  WebMvcLinkBuilder.linkTo(StudentController.class)
				.slash("student").slash(student.getId()).slash("addCourse").withRel("course");
		student.add(courseLink);
		
		Link averageLink =  WebMvcLinkBuilder.linkTo(StudentController.class).
				slash("student").slash(student.getId()).slash("average").withRel("average");
		student.add(averageLink);
		return student;
	}
	
	@PutMapping("student/{studentId}/addCourse") 
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public StudentCourse addToStudentCourses(@PathVariable int studentId, @RequestBody int courseId) {

		return studentCourseService.add(studentId, courseId);
	}
	
	@GetMapping("student/{studentId}/average")
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public float getAverage(@PathVariable int studentId) {
		
		return studentService.getAverage(studentId);
	}
}
