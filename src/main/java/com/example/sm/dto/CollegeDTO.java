package com.example.sm.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CollegeDTO  extends RepresentationModel<CollegeDTO> {

	@NotEmpty(message = "name must not be null")
	@Size(min = 2, message = "college name should have at least 2 characters")
	private String name;

	private int Id;

	// Setters And Getters-------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
}
