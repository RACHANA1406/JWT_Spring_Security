package com.example.spring_security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring_security.model.Employee;
import com.example.spring_security.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateEmployee(@RequestHeader("Authorization") String jwt, @PathVariable("id") int id, @RequestBody Employee employee){
		return employeeService.updateEmployee(jwt, id, employee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@RequestHeader("Authorization") String jwt, @PathVariable("id") int id){
		return employeeService.deleteEmployee(jwt, id);
	}
	
}
