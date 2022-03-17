package com.example.sm.service;

import com.example.sm.dao.CourseRepo;
import com.example.sm.dao.StudentCourseRepo;
import com.example.sm.dao.StudentRepo;
import com.example.sm.model.StudentCourse;
import com.example.sm.model.StudentCourseId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService implements ServiceInterface<StudentCourse> {

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	CourseRepo courseRepo;

	@Override
	public List<StudentCourse> getAll() {

		return studentCourseRepo.findAll();
	}

	@Override
	public StudentCourse add(StudentCourse studentCourse) {

		return studentCourseRepo.save(studentCourse);
	}

	public StudentCourse add(int studentId, int courseId) {

		StudentCourse studentCourse = new StudentCourse();

		studentCourse.setStudent(studentRepo.findById(studentId).get());
		studentCourse.setCourse(courseRepo.findById(courseId).get());

		return add(studentCourse);
	}

	public StudentCourse getByStudentCourseId(StudentCourseId studentCourseId) {
		return studentCourseRepo.findById(studentCourseId).get();
	}

	@Override
	public StudentCourse get(int Id) {
		return null;
	}

	@Override
	public StudentCourse update(StudentCourse studentCourse, int Id) {
		return add(studentCourse);
	}

	@Override
	public void delete(int Id) {
		// studentCourseRepo.deleteById(Id);
	}

	public StudentCourse updateGrade(int studentId, int courseId, float grade) {

		StudentCourseId sci = new StudentCourseId();
		sci.setCourse(courseRepo.findById(courseId).get());
		sci.setStudent(studentRepo.findById(studentId).get());

		StudentCourse studentCourse = getByStudentCourseId(sci);

		studentCourse.setGrade(grade);

		return add(studentCourse);
	}
}
