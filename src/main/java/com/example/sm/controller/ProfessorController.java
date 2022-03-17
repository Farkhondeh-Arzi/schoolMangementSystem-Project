package com.example.sm.controller;

import com.example.sm.dto.CourseDTO;
import com.example.sm.dto.ProfessorDTO;
import com.example.sm.dto.StudentDTO;
import com.example.sm.service.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class ProfessorController {

	private final ProfessorService profService;

	private final StudentCourseService studentCourseService;


	public ProfessorController(ProfessorService profService, StudentCourseService studentCourseService) {

		this.studentCourseService = studentCourseService;
		this.profService = profService;
	}

	@GetMapping("/colleges/{collegeId}/profs")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CollectionModel<ProfessorDTO> getAll(@PathVariable int collegeId,
											 @RequestParam(defaultValue = "0") Integer pageNo,
											 @RequestParam(defaultValue = "10") Integer pageSize) {

		List<ProfessorDTO> profs = profService.getByCollege(collegeId, pageNo, pageSize);

		for (ProfessorDTO prof : profs) {
			Link profLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
					.slash(prof.getId()).withSelfRel();
			prof.add(profLink);

			Link profStudentsLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
					.slash(prof.getId()).slash("students").withRel("students");
			prof.add(profStudentsLink);

			Link profCoursesLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
					.slash(prof.getId()).slash("courses").withRel("courses");
			prof.add(profCoursesLink);

			Link gradeLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
					.slash(prof.getId()).slash("setGrade").withRel("grade");
			prof.add(gradeLink);

			Link averageLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof ")
					.slash(prof.getId()).slash("students").slash("average").withRel("grade");
			prof.add(averageLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("colleges").slash(collegeId)
				.slash("profs").withSelfRel();
        return CollectionModel.of(profs, link);
	}

	@PostMapping("/colleges/{collegeId}/profs")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ProfessorDTO addToProfs(@Valid @RequestBody ProfessorDTO profDTO) {

		return profService.add(profDTO);
	}

	@PutMapping("/colleges/{collegeId}/profs/{Id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ProfessorDTO updateProf(@RequestBody ProfessorDTO profDTO, @PathVariable int Id) {
		return profService.update(profDTO, Id);
	}

	@DeleteMapping("/colleges/{collegeId}/profs/{Id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteProf(@PathVariable int Id) {
		profService.delete(Id);
	}

	@GetMapping("prof/{profId}")
	@PreAuthorize("hasAuthority('PROFESSOR') and @userSecurity.hasUserId(authentication, #studentId)")
	public ProfessorDTO getProf(@PathVariable int profId) {

		ProfessorDTO prof = profService.get(profId);
		Link profStudentsLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
				.slash(prof.getId()).slash("students").withRel("students");
		prof.add(profStudentsLink);

		Link profCoursesLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof")
				.slash(prof.getId()).slash("courses").withRel("courses");
		prof.add(profCoursesLink);

		Link gradeLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof").slash(prof.getId())
				.slash("setGrade").withRel("grade");
		prof.add(gradeLink);

		Link averageLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash("prof ")
				.slash(prof.getId()).slash("students").slash("average").withRel("grade");
		prof.add(averageLink);
		return prof;
	}

	@GetMapping("prof/{profId}/students")
	@PreAuthorize("hasAuthority('PROFESSOR') and @userSecurity.hasUserId(authentication, #studentId)")
	public List<StudentDTO> getStudents(@PathVariable int profId) {

		return profService.getStudentDTOs(profId);
	}

	@GetMapping("prof/{profId}/courses")
	@PreAuthorize("hasAuthority('PROFESSOR') and @userSecurity.hasUserId(authentication, #studentId)")
	public List<CourseDTO> getCourses(@PathVariable int profId) {

		return profService.getCourseDTOs(profId);
	}

	@PutMapping("prof/{profId}/setGrade")
	@PreAuthorize("hasAuthority('PROFESSOR') and @userSecurity.hasUserId(authentication, #studentId)")
	public void setGrade(@RequestBody List<Integer> details) {

		int studentId = details.get(0);
		int courseId = details.get(1);
		int grade = details.get(2);
		studentCourseService.updateGrade(studentId, courseId, grade);
	}

	@GetMapping("prof/{Id}/students/average")
	@PreAuthorize("hasAuthority('PROFESSOR') and @userSecurity.hasUserId(authentication, #studentId)")
	public float getStudentsAverage(@PathVariable int Id) {
		return profService.getStudentsAverage(Id);
	}
}
