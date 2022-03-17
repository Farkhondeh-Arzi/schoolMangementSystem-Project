package com.example.sm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class College {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(unique = true)
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "college")
	private List<Student> students;

	@JsonIgnore
	@OneToMany(mappedBy = "college")
	private List<Professor> professors;

	@JsonIgnore
	@OneToMany(mappedBy = "college")
	private List<Course> courses;

	// Setters And Getters-------------------------------
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "College [Id=" + Id + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof College)) return false;

		College other = (College)obj;
		return this.Id == other.Id;
	}
}