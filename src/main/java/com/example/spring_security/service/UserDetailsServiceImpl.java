package com.example.spring_security.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.spring_security.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
	}
}
