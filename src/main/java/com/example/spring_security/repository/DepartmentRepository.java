package com.example.spring_security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.spring_security.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	
}
