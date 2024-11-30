package com.example.spring_security.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.spring_security.jwt.JwtUtils;
import com.example.spring_security.model.Department;
import com.example.spring_security.model.Employee;
import com.example.spring_security.repository.DepartmentRepository;
import com.example.spring_security.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public ResponseEntity<?> getAllEmp() {
		List<Employee> employees=employeeRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	public ResponseEntity<?> getEmployeeById(int id) {
		try {
			Employee employee=employeeRepository.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(employee);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Not Found");
		}
	}

	public ResponseEntity<?> updateEmployee(String jwt, int id, Employee employee) {
		String token=jwt.substring(7);
		String username=jwtUtils.extractUsername(token);
		Optional<Employee> empOptional=employeeRepository.findById(id);
		if (empOptional.isPresent()) {
			Employee updatedEmp=empOptional.get();
			if (updatedEmp.getUsername().equals(username)) {
				if (employee.getFullName()!=null) {
					updatedEmp.setFullName(employee.getFullName());
				}
				if (employee.getUsername()!=null) {
					updatedEmp.setUsername(employee.getUsername());
				}
				if (employee.getPassword()!=null) {
					updatedEmp.setPassword(employee.getPassword());
				}
				if (employee.getRole()!=null) {
					updatedEmp.setRole(employee.getRole());
				}
				if (employee.getDepartment()!=null) {
					int deptId=employee.getDepartment().getDeptId();
					try {
						Department dept=departmentRepository.findById(deptId).get();
						updatedEmp.setDepartment(dept);
					} 
					catch (Exception e) {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found");
					}
				}   
				updatedEmp=employeeRepository.save(updatedEmp);
				return ResponseEntity.status(HttpStatus.OK).body(updatedEmp);
			}
			else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't update other user's details!!!");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}
	}

	public ResponseEntity<?> deleteEmployee(String jwt, int id) {
		String token=jwt.substring(7);
		String username=jwtUtils.extractUsername(token);
		Optional<Employee> empOptional=employeeRepository.findById(id);
		if (empOptional.isPresent()) {
			Employee updatedEmp=empOptional.get();
			if (updatedEmp.getUsername().equals(username)) {
				employeeRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Successfully!!!");
			}
			else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't delete other users!!!");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}
	}

	
}
