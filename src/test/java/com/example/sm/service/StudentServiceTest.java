package com.example.sm.service;

import com.example.sm.convertos.Convertor;
import com.example.sm.dao.CollegeRepo;
import com.example.sm.dao.StudentRepo;
import com.example.sm.dto.StudentDTO;
import com.example.sm.model.Course;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private Convertor<Student, StudentDTO> studentConvertor;

    @Mock
    CollegeRepo collegeRepo;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    @Captor
    ArgumentCaptor<Student> studentCaptor;

    @Test
    void getAll() {
        studentService.getAll();
        verify(studentRepo, atLeast(1)).findAll();
    }

    @Test
    void add() {
        Student student = new Student();

        studentService.add(studentConvertor.convertToDTO(student, new StudentDTO()));

        verify(studentRepo, atLeast(1)).save(studentCaptor.capture());

        assertThat(studentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void get() {

        given(studentRepo.findById(anyInt())).willReturn(java.util.Optional.of(new Student()));

        studentService.get(1);
        verify(studentRepo, atLeast(1)).findById(1);
    }

    @Test
    void update() {
        Student student = new Student();

        given(studentRepo.findById(anyInt())).willReturn(java.util.Optional.of(new Student()));

        studentService.update(studentConvertor.convertToDTO(student, new StudentDTO()), 1);

        verify(studentRepo, atLeast(1)).save(studentCaptor.capture());

        assertThat(studentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void delete() {

        given(studentRepo.findById(anyInt())).willReturn(java.util.Optional.of(new Student()));

        studentService.delete(1);

        verify(studentRepo, atLeast(1)).deleteById(1);
    }

    @Test
    void getAverage() {

        // Make Student
        Student student = new Student();

        StudentCourse studentCourse1 = new StudentCourse();
        StudentCourse studentCourse2 = new StudentCourse();

        Course course1 = new Course();
        course1.setStudentCourses(new ArrayList<>());
        course1.setStudentCourses(studentCourse1);
        course1.setUnit(2);

        Course course2 = new Course();
        course2.setStudentCourses(new ArrayList<>());
        course2.setStudentCourses(studentCourse2);
        course2.setUnit(2);

        studentCourse1.setCourse(course1);
        studentCourse1.setGrade(20);
        studentCourse2.setCourse(course2);
        studentCourse2.setGrade(18);

        student.setStudentCourses(Arrays.asList(studentCourse1, studentCourse2));

        given(studentRepo.findById(1)).willReturn(java.util.Optional.of(student));

        assertThat(studentService.getAverage(1)).isEqualTo(19);
    }
}