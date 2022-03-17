package com.example.sm.convertos;

import com.example.sm.dto.CourseDTO;
import com.example.sm.model.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseConvertor implements Convertor<Course, CourseDTO> {

    @Override
    public void convertToEntity(Course entity, CourseDTO dto) {

        entity.setName(dto.getName());
        entity.setUnit(dto.getUnit());
        entity.setCollege(dto.getCollege());
        entity.setProfessor(dto.getProfessor());

    }

    @Override
    public CourseDTO convertToDTO(Course entity) {

        CourseDTO dto = new CourseDTO();

        dto.setId(entity.getId());
        dto.setCollege(entity.getCollege());
        dto.setUnit(entity.getUnit());
        dto.setProfessor(entity.getProfessor());
        dto.setName(entity.getName());

        return null;
    }

    @Override
    public List<CourseDTO> convertAllToDTO(List<Course> entities) {

        List<CourseDTO> dtos = new ArrayList<>();

        for (Course course : entities) {
            dtos.add(convertToDTO(course));
        }

        return dtos;
    }
}
