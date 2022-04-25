package com.example.sm.controller;

import com.example.sm.dto.CollegeDTO;
import com.example.sm.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/colleges")
public class CollegeController {

	@Autowired
	CollegeService collegeService;

	@GetMapping()
	public CollectionModel<CollegeDTO> getAllColleges() {

		List<CollegeDTO> colleges = collegeService.getAll();

		for (CollegeDTO college : colleges) {
			
			Link selfLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
			college.add(selfLink);
			
			Link studentLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("students").withRel("students");
			college.add(studentLink);
			
			Link profLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("profs").withRel("professors");
			college.add(profLink);
			
			Link courseLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("courses").withRel("courses");
			college.add(courseLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).withSelfRel();
		return CollectionModel.of(colleges, link);
	}

	@PostMapping()
	public CollegeDTO addToColleges(@Valid @RequestBody CollegeDTO collegeDTO) {

		CollegeDTO college = collegeService.add(collegeDTO);
		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
		college.add(link);
		return college;
	}

	@PutMapping("{Id}")
	public CollegeDTO updateCollege(@Valid @RequestBody CollegeDTO collegeDTO, @PathVariable int Id) {

		CollegeDTO college = collegeService.update(collegeDTO, Id);
		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
		college.add(link);
		return college;
	}

	@DeleteMapping("{Id}")
	public void deleteCollege(@PathVariable int Id) {
		collegeService.delete(Id);
	}
}
