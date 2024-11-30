package com.example.spring_security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring_security.model.Employee;
import com.example.spring_security.service.PublicService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private PublicService publicService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello, Unauthenticaed (Public)!!!";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Employee employee) {
		return publicService.register(employee);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Employee employee){
		return publicService.authencatie(employee);
	}
	
}
