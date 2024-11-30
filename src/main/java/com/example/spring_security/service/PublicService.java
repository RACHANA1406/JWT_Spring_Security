package com.example.spring_security.service;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.spring_security.model.Department;
import com.example.spring_security.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_security.jwt.JwtUtils;
import com.example.spring_security.repository.DepartmentRepository;
import com.example.spring_security.repository.EmployeeRepository;

@Service
public class PublicService {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public ResponseEntity<?> register(Employee employee) {
		Optional<Employee> emp=employeeRepository.findByUsername(employee.getUsername());
		if (emp.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.. Please Login!!");
		}	
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		if (employee.getDepartment()!=null) {
			int deptid=employee.getDepartment().getDeptId();
			try {
				Department dept=departmentRepository.findById(deptid).get();
				employee.setDepartment(dept);
			}
			catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found...Failed to Register!!!");
			}
		}
		employee=employeeRepository.save(employee);
		String token= jwtUtils.generateToken(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(token);
	}
	
	public ResponseEntity<?> authencatie(Employee employee) { //login
		Optional<Employee> registeredEmployee=employeeRepository.findByUsername(employee.getUsername());
		if (registeredEmployee.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!! Please Register!!!");
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							employee.getUsername(), employee.getPassword()
					)
			);
			Employee emp=registeredEmployee.get();
			String token=jwtUtils.generateToken(emp);
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
}
