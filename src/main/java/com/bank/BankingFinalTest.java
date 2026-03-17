package com.bank;

import java.math.BigDecimal;
import java.util.Scanner;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;
import com.bank.service.AlertService;
import com.bank.service.TransactionService;

public class BankingFinalTest {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		AccountRepository accRepo = new AccountRepository();
		AccountService accService = new AccountService(accRepo);
		TransactionRepository trxRepo = new TransactionRepository();
		AlertService alertService = new AlertService(new BigDecimal("1000"));
		TransactionService trxService = new TransactionService(accService, trxRepo, alertService);
		
		System.out.println("===========================================================================");
		System.out.println("WELCOME TO OUR BANKING APPLICATION");
		System.out.println("===========================================================================");
		
		boolean running = true;
		
		while(running) {
			
			System.out.println("\nChoose an option");
			System.out.println("1. Create Account");
			System.out.println("2. Deposite Money");
			System.out.println("3. Withdraw Money");
			System.out.println("4. Transfer Money");
			System.out.println("5  Show Account Details");
			System.out.println("6. List All Accounts");
			System.out.println("7. Exit");
			
			System.out.println("Enter Choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
				case 1:
					System.out.println("Enter name: ");
					String name = sc.nextLine();
					System.out.println("Enter email: ");
					String email = sc.nextLine();
					System.out.println("Enter Opening Balance");
					BigDecimal openingBalance = sc.nextBigDecimal();
					try {
						Account acc = accService.createAccount(name, email, openingBalance);
						System.out.println("Account created Successfully..! "+acc.getAccountNumber());
					} catch (InvalidAmountException e) {
						 
						e.printStackTrace();
					}
					break;
				
				case 2:
					System.out.println("Enter Account Number..!");
					String deptAcc = sc.nextLine();
					System.out.println("Enter the Amount to Deposite..!");
					BigDecimal deptAmount = sc.nextBigDecimal();
					try {
						trxService.deposite(deptAcc, deptAmount);
						System.out.println("Deposite Successfull");
					} catch (AccountNotFoundException | InvalidAmountException e) {
						 
						e.printStackTrace();
					}
					break;
					
				case 3:
					System.out.println("Enter Account Number..!");
					String withAcc = sc.nextLine();
					System.out.println("Enter the Amount to Deposite..!");
					BigDecimal withAmt = sc.nextBigDecimal();
					try {
						trxService.withdraw(withAcc, withAmt);
						System.out.println("Withdraw Successfull");
					} catch (AccountNotFoundException | InvalidAmountException | InsufficientBalanceException e) {
						
						e.printStackTrace();
					}
					break;
					
				
				case 4:
					System.out.println("Enter Sender Account Number..!");
					String sender = sc.nextLine();
					System.out.println("Enter Recevier Account Number..!");
					String receiver = sc.nextLine();
					System.out.println("Enter Amount for transfer..!");
					BigDecimal tAmt = sc.nextBigDecimal();
					try {
						trxService.transfer(sender, receiver, tAmt);
						System.out.println("Transfer Successfull");
					} catch (InvalidAmountException | AccountNotFoundException | InsufficientBalanceException e) {
						 
						e.printStackTrace();
					}
					break;
					
				case 5:
					System.out.println("Enter Account Number to get Details..!");
					String accNo = sc.nextLine();
					try {
						Account account = accService.getAccount(accNo);
						System.out.println("Account Details: "+account);
					} catch (AccountNotFoundException e) {
						 
						e.printStackTrace();
					}
					break;
					
				case 6:
					System.out.println("Listing All the Accounts..!");
					for(Account account : accService.listAllAccounts()) {
						System.out.println(account);
					}
					break;
					
				case 7: 
					System.out.println("Thank you for using for our Banking Application..!");
					running = false;
					break;
					
				default:
					System.out.println("Invalid Choice. Please Try Again..!");
					
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}