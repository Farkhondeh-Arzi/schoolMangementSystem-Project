package com.example.sm.service;

import com.example.sm.convertors.Convertor;
import com.example.sm.dao.CollegeRepo;
import com.example.sm.dao.StudentRepo;
import com.example.sm.dto.StudentDTO;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService implements ServiceInterface<StudentDTO> {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CollegeRepo collegeRepo;

    @Autowired
    private Convertor<Student, StudentDTO> studentConvertor;

    @Override
    public List<StudentDTO> getAll() {
        return studentConvertor.convertAllToDTO(studentRepo.findAll(), StudentDTO.class);
    }

    @Override
    public StudentDTO add(StudentDTO dto) {

        Student student = new Student();

        studentConvertor.convertToEntity(student, dto);
        //set Id before encoding it
        studentRepo.save(student);
        //Set default values
        student.setUsername(String.valueOf(student.getNationalNumber()));
        student.setPassword(passwordEncoder.encode(String.valueOf(student.getId())));
        student.setRole("STUDENT");

        studentRepo.save(student);

        return dto;
    }

    @Override
    public StudentDTO get(int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        return studentConvertor.convertToDTO(student, new StudentDTO());
    }

    @Override
    public StudentDTO update(StudentDTO dto, int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        studentConvertor.convertToEntity(student, dto);
        studentRepo.save(student);

        return dto;
    }

    public StudentDTO updateProfile(MultipartFile file, int Id) throws IOException {
        Student student = getStudent(Id);
        student.setProfile(file.getBytes());

        studentRepo.save(student);

        return studentConvertor.convertToDTO(student, new StudentDTO());
    }

    @Override
    public void delete(int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        studentRepo.deleteById(Id);
    }

    public float getAverage(int Id) {

        List<StudentCourse> studentCourses = getStudent(Id).getStudentCourses();

        float sum = 0;
        int totalUnit = 0;

        for (StudentCourse studentCourse : studentCourses) {
            totalUnit += studentCourse.getCourse().getUnit();
            sum += studentCourse.getGrade() * studentCourse.getCourse().getUnit();
        }

        return sum / totalUnit;
    }

    public List<StudentDTO> getByCollege(int collegeId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Student> students = studentRepo.findByCollege(collegeRepo.findById(collegeId).orElse(null), pageable);
        return studentConvertor.convertAllToDTO(students, StudentDTO.class);
    }

    private Student getStudent(int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        return student;
    }
}
