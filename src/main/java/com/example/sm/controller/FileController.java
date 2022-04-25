package com.example.sm.controller;

import com.example.sm.message.ResponseMessage;
import com.example.sm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
	
	@Autowired
	UserService userService;

	@PostMapping(value = {"student/{userId}/upload", "prof/{userId}/upload"})
	public ResponseEntity<ResponseMessage> uploadFile(@PathVariable int userId, @RequestParam("file") MultipartFile file) {
		String message;
		try {
			userService.updateProfile(file, userId);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping(value = {"student/{userId}/profile", "prof/{userId}/profile"})
	public ResponseEntity<byte[]> getFile(@PathVariable int userId) {
		byte[] data = userService.get(userId).getProfile();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;")
				.body(data);
	}
}
