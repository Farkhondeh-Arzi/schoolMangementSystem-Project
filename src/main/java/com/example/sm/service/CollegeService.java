package com.example.sm.service;

import com.example.sm.convertors.Convertor;
import com.example.sm.dao.CollegeRepo;
import com.example.sm.dto.CollegeDTO;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService implements ServiceInterface<CollegeDTO> {

    @Autowired
    private CollegeRepo collegeRepo;

    @Autowired
    private Convertor<College, CollegeDTO> convertor;

    @Override
    public List<CollegeDTO> getAll() {

        return convertor.convertAllToDTO(collegeRepo.findAll(), CollegeDTO.class);
    }

    @Override
    public CollegeDTO add(CollegeDTO collegeDTO) {
        College college = convertor.convertToEntity(new College(), collegeDTO);

        collegeRepo.save(college);

        return collegeDTO;
    }

    @Override
    public CollegeDTO get(int Id) {
        College college = collegeRepo.findById(Id).orElse(null);
        if (college == null)
            throw new RecordNotFoundException("Not valid ID");

        return convertor.convertToDTO(college, new CollegeDTO());
    }

    @Override
    public CollegeDTO update(CollegeDTO collegeDTO, int Id) {
        College college = collegeRepo.findById(Id).orElse(null);
        if (college == null)
            throw new RecordNotFoundException("Not valid ID");

        convertor.convertToEntity(college, collegeDTO);
        collegeRepo.save(college);

        return collegeDTO;
    }

    @Override
    public void delete(int Id) {
        College college = collegeRepo.findById(Id).orElse(null);
        if (college == null)
            throw new RecordNotFoundException("Not valid ID");
        collegeRepo.deleteById(Id);
    }
}