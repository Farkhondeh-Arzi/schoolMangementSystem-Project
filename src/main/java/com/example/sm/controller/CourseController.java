package com.example.sm.controller;

import com.example.sm.dto.CourseDTO;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	CollegeService collegeService;

	@GetMapping("/colleges/{collegeId}/courses")
	public CollectionModel<CourseDTO> getAll(@PathVariable int collegeId) {

		//List<CourseDTO> courses = collegeService.getCourseDTOs(collegeId);
		List<CourseDTO> courses = courseService.getByCollege(collegeId);

		for (CourseDTO course : courses) {
			Link selfLink = WebMvcLinkBuilder.linkTo(CourseController.class)
					.slash("colleges").slash(collegeId).slash("courses").slash(course.getId()).withSelfRel();
			course.add(selfLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(CourseController.class)
				.slash("colleges").slash(collegeId).slash("courses").withSelfRel();
		return CollectionModel.of(courses, link);
	}

	@PostMapping("/colleges/{collegeId}/courses")
	public CourseDTO addToCourses(@PathVariable int collegeId, @Valid @RequestBody CourseDTO courseDTO) {

		return courseService.add(courseDTO);
	}

	@PutMapping("/colleges/{collegeId}/courses/{courseId}")
	public CourseDTO updateCourse(@PathVariable int collegeId, @RequestBody CourseDTO courseDTO, @PathVariable int courseId) {
		return courseService.update(courseDTO, courseId);
	}

	@DeleteMapping("/colleges/{collegeId}/courses/{courseId}")
	public void deleteCourse(@PathVariable int Id, @PathVariable int courseId) {
		courseService.delete(courseId);
	}
}
