package com.mainspring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.service.RulesService;

@RestController
public class RulesController {
	
	@Autowired
	private RulesService rulesService;
	
	@GetMapping("/evaluateMinBal")
	public ResponseEntity<String> evaluateMinBalance(@RequestParam double balance){
		return new ResponseEntity<>(rulesService.evaluateMinBalance(balance), HttpStatus.OK);
	}
	
	@GetMapping("/getServiceCharges")
	public ResponseEntity<Float> getServiceCharges(){
		return new ResponseEntity<>(rulesService.getServiceCharge(), HttpStatus.OK);
	}

}
