package com.example.sm.convertos;

import com.example.sm.dto.CollegeDTO;
import com.example.sm.model.College;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollegeConvertor implements Convertor<College, CollegeDTO> {

    @Override
    public void convertToEntity(College college, CollegeDTO dto) {

        college.setName(dto.getName());

    }

    @Override
    public CollegeDTO convertToDTO(College entity) {

        CollegeDTO dto = new CollegeDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    @Override
    public List<CollegeDTO> convertAllToDTO(List<College> entities) {

        List<CollegeDTO> dtos = new ArrayList<>();

        for (College college : entities) {
            dtos.add(convertToDTO(college));
        }

        return dtos;
    }
}
