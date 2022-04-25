
package com.example.sm.dao;

import com.example.sm.model.Admin;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import com.example.sm.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepo {

	private final StudentRepo studentRepo;
	private final ProfessorRepo profRepo;
	private final AdminRepo adminRepo;
	private final ManagerRepo managerRepo;
	
	public UserRepo(StudentRepo studentRepo, ProfessorRepo profRepo, AdminRepo adminRepo, ManagerRepo managerRepo) {
		this.studentRepo = studentRepo;
		this.profRepo = profRepo;
		this.adminRepo = adminRepo;
		this.managerRepo = managerRepo;
	}

	public User findByUsername(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student != null) return student;
		Professor prof = profRepo.findByUsername(username);
		if (prof != null) return prof;
		Admin admin = adminRepo.findByUsername(username);
		if (admin != null) return admin;
		return managerRepo.findByUsername(username);
	}

	public List<User> findAll() {

		List<User> users = new ArrayList<>();
		users.addAll(studentRepo.findAll());
		users.addAll(profRepo.findAll());
		return users;
	}

	public User findById(int Id) {
		Student student = studentRepo.findById(Id).orElse(null);
		if (student != null) return student;
		Professor prof = profRepo.findById(Id).orElse(null);
		if (prof != null) return prof;
		return adminRepo.findById(Id).orElse(null);
	}
}
