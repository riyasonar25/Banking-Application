package com.bank;

import java.math.BigDecimal;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;
import com.bank.service.AlertService;
import com.bank.service.TransactionService;

public class AccountTest {

	public static void main(String[] args) {

		AccountRepository repo = new AccountRepository();
		AccountService service = new AccountService(repo);

		TransactionRepository txRepo = new TransactionRepository();
		AlertService alertService = new AlertService(new BigDecimal("1000"));
		TransactionService txService = new TransactionService(service, txRepo, alertService);

		try {

			System.out.println("Created Accounts..!");

			Account account1 = service.createAccount("Riya", "riyasonar120@gmail.com", new BigDecimal("5000"));
			Account account2 = service.createAccount("RiyaS2", "riyasonar00@gmail.com", new BigDecimal("1000"));

			System.out.println(account1);
			System.out.println(account2);

			System.out.println("========================================================");
			System.out.println("Testing Withdraw Transaction");

			// Withdraw to trigger email alert
			txService.withdraw(account1.getAccountNumber(), new BigDecimal("4500"));

			System.out.println("========================================================");

		} catch (InvalidAmountException | AccountNotFoundException | InsufficientBalanceException e) {
		    e.printStackTrace();
		}
		}

	}

