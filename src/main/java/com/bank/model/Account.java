package com.bank.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class Account {
	
	private static final AtomicLong COUNTER= new AtomicLong(10000000L);
	private String accountNumber;
	private String holderName;
	private String email;
	private BigDecimal openingBalance;
	
	public Account(String holderName, String email,BigDecimal openingBalance) {
		this.accountNumber = String.valueOf(COUNTER.getAndIncrement());
		this.holderName = holderName;
		this.email = email;
		this.openingBalance = openingBalance == null ? BigDecimal.ZERO : openingBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void credit(BigDecimal balance){
		this.openingBalance = this.openingBalance.add(balance);
	}
	
	public void debit(BigDecimal balance){
		this.openingBalance = this.openingBalance.subtract(balance);
	}
	
	
	public String toString(){
		return accountNumber+" "+holderName+" "+email+" "+openingBalance;
	}
	
	

}