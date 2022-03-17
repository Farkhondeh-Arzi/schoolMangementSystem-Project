package com.example.sm.service;

import com.example.sm.convertos.CourseConvertor;
import com.example.sm.convertos.ProfessorConvertor;
import com.example.sm.convertos.StudentConvertor;
import com.example.sm.dao.CollegeRepo;
import com.example.sm.dao.ProfessorRepo;
import com.example.sm.dto.CourseDTO;
import com.example.sm.dto.ProfessorDTO;
import com.example.sm.dto.StudentDTO;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Course;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService implements ServiceInterface<ProfessorDTO> {

    @Autowired
    ProfessorRepo profRepo;

    @Autowired
    CollegeRepo collegeRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ProfessorConvertor professorConvertor;

    @Autowired
    StudentConvertor studentConvertor;

    @Autowired
    CourseConvertor courseConvertor;

    @Override
    public List<ProfessorDTO> getAll() {

        return professorConvertor.convertAllToDTO(profRepo.findAll());
    }

    @Override
    public ProfessorDTO add(ProfessorDTO dto) {
        Professor prof = new Professor();
        professorConvertor.convertToEntity(prof, dto);

        //Save to set Id for creating default password
        profRepo.save(prof);
        prof.setUsername(String.valueOf(dto.getNationalNumber()));
        prof.setPassword(passwordEncoder.encode(String.valueOf(dto.getId())));
        prof.setRole("PROFESSOR");

        profRepo.save(prof);

        return dto;
    }

    @Override
    public ProfessorDTO get(int Id) {
        Professor professor = profRepo.findById(Id).orElse(null);
        if (professor == null) throw new RecordNotFoundException("Not valid ID");

        return professorConvertor.convertToDTO(professor);
    }

    @Override
    public ProfessorDTO update(ProfessorDTO dto, int Id) {
        Professor prof = profRepo.findById(Id).orElse(null);
        if (prof == null) throw new RecordNotFoundException("Not valid ID");

        professorConvertor.convertToEntity(prof, dto);
        profRepo.save(prof);

        return dto;
    }

    public ProfessorDTO updateProfile(MultipartFile file, int Id) throws IOException {
        Professor professor = getProfessor(Id);
        professor.setProfile(file.getBytes());

        return professorConvertor.convertToDTO(professor);
    }

    @Override
    public void delete(int Id) {
        Professor professor = profRepo.findById(Id).orElse(null);
        if (professor == null) throw new RecordNotFoundException("Not valid ID");

        profRepo.deleteById(Id);
    }

    public List<StudentDTO> getStudentDTOs(int Id) {

        return studentConvertor.convertAllToDTO(getStudents(Id));
    }

    private List<Student> getStudents(int Id) {

        List<Student> profStudents = new ArrayList<>();
        List<Course> courses = getCourses(Id);

        for (Course course : courses) {

            List<StudentCourse> studentCourses = course.getStudentCourses();

            for (StudentCourse studentCourse : studentCourses) {
                profStudents.add(studentCourse.getStudent());
            }
        }

        return profStudents;
    }

    public List<CourseDTO> getCourseDTOs(int profId) {
        return courseConvertor.convertAllToDTO(getProfessor(profId).getCourses());
    }

    private List<Course> getCourses(int profId) {
        return getProfessor(profId).getCourses();
    }

    public float getStudentsAverage(int Id) {

        List<Student> students = getStudents(Id);

        float totalSum = 0;

        for (Student student : students) {
            List<StudentCourse> studentCourses = student.getStudentCourses();

            float sum = 0;
            int totalUnit = 0;

            for (StudentCourse studentCourse : studentCourses) {
                totalUnit += studentCourse.getCourse().getUnit();
                sum += studentCourse.getGrade() * studentCourse.getCourse().getUnit();
            }

            totalSum += sum / totalUnit;
        }

        return totalSum / students.size();
    }

    public List<ProfessorDTO> getByCollege(int collegeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Professor> professors = profRepo.findByCollege(collegeRepo.findById(collegeId).orElse(null), pageable);

        return professorConvertor.convertAllToDTO(professors);
    }


    private Professor getProfessor(int Id) {
        Professor professor = profRepo.findById(Id).orElse(null);
        if (professor == null) throw new RecordNotFoundException("Not valid ID");

        return professor;
    }
}
