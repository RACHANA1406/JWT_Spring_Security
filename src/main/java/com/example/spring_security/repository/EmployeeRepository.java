package com.example.spring_security.repository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.spring_security.model.Department;
import com.example.spring_security.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Optional<Employee> findByUsername(String username);
	ArrayList<Employee> findByDepartment(Department department);
}
