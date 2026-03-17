package com.bank.service;

import java.math.BigDecimal;

import com.bank.model.Account;
import com.bank.util.EmailUtil;

public class AlertService {
	
	private final BigDecimal threshold;
	
	public AlertService(BigDecimal threshold) {
		this.threshold = threshold;
	}
	
	public void checkBalance(Account account){
		if(account.getOpeningBalance().compareTo(threshold) <= 0) {
			String subject = "Low Balance Alert: "+account.getAccountNumber();
			String message = "Dear "+account.getHolderName()+", \n\n Your Account Balance is Low: "+account.getOpeningBalance()+
					"\nPlease maintain the minimum balance.";
			EmailUtil.sendEmail(account.getEmail(),subject,message);
		}
		
	}
}