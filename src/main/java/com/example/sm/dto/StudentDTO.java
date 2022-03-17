package com.example.sm.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import com.example.sm.model.College;
import org.springframework.hateoas.RepresentationModel;

public class StudentDTO   extends RepresentationModel<CollegeDTO> {

	@NotEmpty(message = "forename could not be empty")
	@Size(min = 1, max = 85, message = "forename should have at least 1 character")
	private String forename;

	@NotEmpty(message = "surname could not be empty")
	@Size(min = 1, max = 85, message = "surname should have at least 1 character")
	private String surname;

	@Range(min = 1, max = 9999999999L)
	private long nationalNumber;

	private int Id;

	@JsonIgnore
	private byte[] profile;

	private College college;

	// Setters And Getters-------------------------------
	
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getNationalNumber() {
		return nationalNumber;
	}

	public void setNationalNumber(long nationalNumber) {
		this.nationalNumber = nationalNumber;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}
}
