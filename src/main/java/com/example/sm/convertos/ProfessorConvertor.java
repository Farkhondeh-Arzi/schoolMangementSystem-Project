package com.example.sm.convertos;

import com.example.sm.dto.ProfessorDTO;
import com.example.sm.model.Professor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorConvertor implements Convertor<Professor, ProfessorDTO> {

    @Override
    public void convertToEntity(Professor professor, ProfessorDTO dto) {

        professor.setForename(dto.getForename());
        professor.setProfile(dto.getProfile());
        professor.setCollege(dto.getCollege());
        professor.setNationalNumber(dto.getNationalNumber());

    }

    @Override
    public ProfessorDTO convertToDTO(Professor professor) {
        ProfessorDTO dto = new ProfessorDTO();

        dto.setCollege(professor.getCollege());
        dto.setId(professor.getId());
        dto.setForename(professor.getForename());
        dto.setSurname(professor.getSurname());
        dto.setNationalNumber(professor.getNationalNumber());
        dto.setProfile(professor.getProfile());

        return dto;
    }

    @Override
    public List<ProfessorDTO> convertAllToDTO(List<Professor> entities) {

        List<ProfessorDTO> dtos = new ArrayList<>();

        for (Professor professor : entities) {
            dtos.add(convertToDTO(professor));
        }

        return dtos;
    }
}
