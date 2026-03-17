package com.bank.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;

public class AccountService {
	
	private final AccountRepository repo;
	
	public AccountService(AccountRepository repo) {
		this.repo = repo;
	}
	
	public Account createAccount(String name, String email,BigDecimal openingBalance) throws InvalidAmountException {
		
		if(openingBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidAmountException("Opening balance should not be Negative");
		}
		
		Account acc = new Account(name,email,openingBalance);
		repo.save(acc);
		return acc;
	}
	
	public Account getAccount(String accNo) throws AccountNotFoundException {
		Account account = repo.findAccountNumber(accNo);
		
		if(account == null) {
			throw new AccountNotFoundException("Account not found: "+accNo);
		}
		
		return account;
	}
	
	
	public Collection<Account>listAllAccounts(){
		return repo.findAll();
	}
	
	
	
	

}

