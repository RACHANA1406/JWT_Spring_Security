package com.example.spring_security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring_security.service.DepartmentService;
import com.example.spring_security.service.EmployeeService;

@RestController
@RequestMapping("/auth/user")
public class AuthPublicController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello, You are Authenticaed!!!";
	}
	
	@GetMapping("/departments")
	public ResponseEntity<?> getAllDept(){
		return departmentService.getAllDept();
	}
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmp(){
		return employeeService.getAllEmp();
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id){
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/departments/{id}")
	public ResponseEntity<?> getDeptById(@PathVariable("id") int id){
		return departmentService.getDeptById(id);
	}
	
	@GetMapping("/departments/{id}/employees")
	public ResponseEntity<?> getEmployeesByDeptId(@PathVariable("id") int id){
		return departmentService.getEmployeesByDeptId(id);
	}
	
}
