package com.mainspring.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public List<Employee> getData() {
		return employeeService.getData();
	}
	
	@PostMapping
	public void postData(@RequestBody Employee employee) {
		employeeService.postData(employee);
	}
	
	@DeleteMapping(path = "{Employeeid}")
	public void deleteData(@PathVariable("Employeeid")int Employeeid) {
		employeeService.deleteData(Employeeid);
	}
	
	@PutMapping(path = "{Employeeid}")
	public void putData(
			@PathVariable("Employeeid") int id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String mail){
		employeeService.putData(id,name,mail);		
	}

}
