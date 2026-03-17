package com.bank.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bank.model.Account;

public class AccountRepository {
	
	private final Map<String,Account> accounts = new HashMap<>();
	
	public void save(Account acc){
		accounts.put(acc.getAccountNumber(), acc);
	}
	
	public Account findAccountNumber(String accountNumber){
		return accounts.get(accountNumber);
	}
	
	public Collection<Account> findAll(){
		return accounts.values();
	}
	

}