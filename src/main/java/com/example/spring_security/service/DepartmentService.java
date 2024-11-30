package com.example.spring_security.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.spring_security.model.Department;
import com.example.spring_security.model.Employee;
import com.example.spring_security.repository.DepartmentRepository;
import com.example.spring_security.repository.EmployeeRepository;

@Service
public class DepartmentService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public ResponseEntity<?> addDept(Department department){
		Department newDepartment=departmentRepository.save(department);
		return new ResponseEntity<Department>(newDepartment, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> deleteDept(int id){
		try {
			Department dept=departmentRepository.findById(id).get();
			ArrayList<Employee> emp=employeeRepository.findByDepartment(dept);
			for (Employee e: emp ) {
				e.setDepartment(null);
			}
			employeeRepository.saveAll(emp);
			departmentRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Dept Deleted Successfully!!");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found");
		}
		
	}

	public ResponseEntity<?> getAllDept() {
		List<Department> dept=departmentRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(dept);
	}

	public ResponseEntity<?> getDeptById(int id) {
		try {
			Department dept=departmentRepository.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(dept);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found");
		}
	}

	public ResponseEntity<?> updateDept(int id, Department department) {
		try {
			Department updatedDept=departmentRepository.findById(id).get();
			if (department.getDeptName()!=null) {
				ArrayList<Employee> employees=employeeRepository.findByDepartment(updatedDept);
				updatedDept.setDeptName(department.getDeptName());
				updatedDept=departmentRepository.save(updatedDept);
				for (Employee e: employees) {
					e.setDepartment(updatedDept);
				}
				employeeRepository.saveAll(employees);
			}
			return ResponseEntity.status(HttpStatus.OK).body(updatedDept);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found");
		}
	}

	public ResponseEntity<?> getEmployeesByDeptId(int id) {
		try {
			Department dept=departmentRepository.findById(id).get();
			ArrayList<Employee> employees=employeeRepository.findByDepartment(dept);
			if (employees.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No employees found!!!");
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(employees);
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept Not Found");
		}
	}
}
