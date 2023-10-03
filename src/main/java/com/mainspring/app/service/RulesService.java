package com.mainspring.app.service;

import org.springframework.stereotype.Service;

import com.mainspring.app.model.RuleStatus;

@Service
public class RulesService {
	
	private static final Float SERVICE_CHARGE = 250.00F;
	
	public String evaluateMinBalance(double balance) {
		if(balance < 1000) {
			return RuleStatus.DENIED.toString();
		} else
			return RuleStatus.ALLOWED.toString();
	}
	
	public Float getServiceCharge() {
		return SERVICE_CHARGE;
	}

}