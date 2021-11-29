package com.mainspring.app;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getData() {
		return employeeRepository.findAll();
	}

	public void postData(Employee employee) {
		employeeRepository.save(employee);
		
	}

	public void deleteData(int Employeeid) {
		employeeRepository.deleteById(Employeeid);
	}

	@Transactional
	public void putData(int id, String name, String mail) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow
				(()-> new IllegalStateException("Employee ID does not exist"));
		
		if(name!= null && !Objects.equals(employee.getName(), name)) {
			employee.setName(name);			
		}
		
		if(mail!= null && !Objects.equals(employee.getMail(), mail)) {
			employee.setMail(mail);			
		}
		
	}

}
