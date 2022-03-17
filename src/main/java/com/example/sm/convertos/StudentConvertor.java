package com.example.sm.convertos;

import com.example.sm.dto.StudentDTO;
import com.example.sm.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentConvertor implements Convertor<Student, StudentDTO> {

    @Override
    public void convertToEntity(Student student, StudentDTO dto) {

        student.setForename(dto.getForename());
        student.setSurname(dto.getSurname());
        student.setCollege(dto.getCollege());
        student.setNationalNumber(dto.getNationalNumber());

    }

    @Override
    public StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();

        dto.setForename(student.getForename());
        dto.setSurname(student.getSurname());
        dto.setProfile(student.getProfile());
        dto.setId(student.getId());
        dto.setCollege(student.getCollege());
        dto.setNationalNumber(student.getNationalNumber());

        return dto;
    }

    @Override
    public List<StudentDTO> convertAllToDTO(List<Student> entities) {

        List<StudentDTO> dtos = new ArrayList<>();

        for (Student student : entities) {
            dtos.add(convertToDTO(student));
        }

        return dtos;
    }
}
