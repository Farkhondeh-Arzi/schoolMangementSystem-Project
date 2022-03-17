package com.example.sm.service;

import com.example.sm.convertos.CollegeConvertor;
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
    CollegeRepo collegeRepo;

    @Autowired
    CollegeConvertor collegeConvertor;

    @Override
    public List<CollegeDTO> getAll() {

        return collegeConvertor.convertAllToDTO(collegeRepo.findAll());
    }

    @Override
    public CollegeDTO add(CollegeDTO collegeDTO) {
        College college = new College();
        collegeConvertor.convertToEntity(college, collegeDTO);

        collegeRepo.save(college);

        return collegeDTO;
    }

    @Override
    public CollegeDTO get(int Id) {
        College college = collegeRepo.findById(Id).orElse(null);
        if (college == null)
            throw new RecordNotFoundException("Not valid ID");

        return collegeConvertor.convertToDTO(college);
    }

    @Override
    public CollegeDTO update(CollegeDTO collegeDTO, int Id) {
        College college = collegeRepo.findById(Id).orElse(null);
        if (college == null)
            throw new RecordNotFoundException("Not valid ID");

        collegeConvertor.convertToEntity(college, collegeDTO);
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