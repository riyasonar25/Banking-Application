package com.bank.service;

import java.math.BigDecimal;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.repository.TransactionRepository;
import com.bank.util.FileReportUtil;

public class TransactionService {
	
	private final AccountService accountService;
	private final TransactionRepository txRepo;
	private final AlertService alertservice;
	
	
	public TransactionService(AccountService accountService,TransactionRepository txRepo,AlertService alertservice) {
		this.accountService = accountService;
		this.txRepo = txRepo;
		this.alertservice = alertservice;
	}
	
	public void deposite(String accNo, BigDecimal amount) throws AccountNotFoundException, 
	InvalidAmountException
	{
		
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException("Amount must be positive..!");
		}
		
		
		Account account = accountService.getAccount(accNo);
		account.credit(amount);
		txRepo.logTransaction("DEPOSITE", accNo, amount.doubleValue(), null);
		FileReportUtil.writeLine("DEPOSITE | Acc: "+accNo+" | Amount: "+amount);
		alertservice.checkBalance(account);
		
		System.out.println("Deposited : "+amount+" to "+accNo);
		
	}
	
	public void withdraw(String accNo, BigDecimal amount) throws AccountNotFoundException, 
	InvalidAmountException, InsufficientBalanceException
	{
		
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException("Amount must be positive..!");
		}
		
		
		Account account = accountService.getAccount(accNo);
		if(account.getOpeningBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient Balance..!");
		}
		
		account.debit(amount);
		txRepo.logTransaction("WITHDRAW", accNo, amount.doubleValue(), null);
		FileReportUtil.writeLine("WITHDRAW | Acc: "+accNo+" | Amount: "+amount);
		
		alertservice.checkBalance(account);
		
		System.out.println("Withdraw : "+amount+" to "+accNo);
		
	}
	
	public void transfer(String fromAcc, String toAcc,BigDecimal amount) throws InvalidAmountException, AccountNotFoundException, InsufficientBalanceException{
		
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException("Amount must be positive..!");
		}
		
		Account sender = accountService.getAccount(fromAcc);
		Account receiver = accountService.getAccount(toAcc);
		
		if(sender.getOpeningBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient Balance..!");
		}
		
		sender.debit(amount);
		alertservice.checkBalance(sender);
		receiver.credit(amount);
		alertservice.checkBalance(receiver);
		
		txRepo.logTransaction("TRANSFER", fromAcc, amount.doubleValue(), toAcc);
		FileReportUtil.writeLine("TRANSFER | Acc: "+fromAcc+" | Amount: "+amount+" | to "+toAcc);
		System.out.println(amount +" Transfer Successfull from : "+fromAcc+" to "+toAcc);
	}
	
	

}




