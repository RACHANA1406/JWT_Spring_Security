package com.example.spring_security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring_security.model.Department;
import com.example.spring_security.model.Employee;
import com.example.spring_security.service.AdminService;
import com.example.spring_security.service.DepartmentService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/departments")
	public ResponseEntity<?> addDept(@RequestBody Department department) {
		return departmentService.addDept(department);
	}
	
	@PutMapping("/departments/{id}")
	public ResponseEntity<?> updateDept(@PathVariable("id") int id, @RequestBody Department department) {
		return departmentService.updateDept(id, department);
	}
	
	@DeleteMapping("/departments/{id}")
	public ResponseEntity<?> deleteDept(@PathVariable("id") int id) {
		return departmentService.deleteDept(id);
	}
	
	@PutMapping("/admins/{id}")
	public ResponseEntity<?> updateAdmin(@RequestHeader("Authorization") String jwt, @PathVariable("id") int id, @RequestBody Employee employee){
		return adminService.updateAdmin(jwt, id, employee);
	}
	
	
	@DeleteMapping("/admins/{id}")
	public ResponseEntity<?> deleteAdmin(@RequestHeader("Authorization") String jwt, @PathVariable("id") int id){
		return adminService.deleteAdmin(jwt, id);
	}
	
}
