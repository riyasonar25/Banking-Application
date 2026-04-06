package com.bank;

import java.math.BigDecimal;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;
import com.bank.service.AlertService;
import com.bank.service.TransactionService;

public class AccountTest {

    public static void main(String[] args) {

        // ✅ Repository & Services Setup
        AccountRepository repo = new AccountRepository();
        AccountService service = new AccountService(repo);

        TransactionRepository txRepo = new TransactionRepository();
        AlertService alertService = new AlertService(new BigDecimal("1000"));
        TransactionService txService = new TransactionService(service, txRepo, alertService);

        try {

            // ✅ Create Accounts
            System.out.println("Created Accounts..!");

            Account account1 = service.createAccount("Riya", "riyasonar120@gmail.com", new BigDecimal("5000"));
            Account account2 = service.createAccount("RiyaS2", "riyasonar00@gmail.com", new BigDecimal("1000"));

            System.out.println(account1);
            System.out.println(account2);

            System.out.println("========================================================");

            // ✅ Withdraw Test
            System.out.println("Testing Withdraw Transaction");

            txService.withdraw(account1.getAccountNumber(), new BigDecimal("4500"));

            // Check updated balance
            System.out.println("Updated Account:");
            System.out.println(service.getAccount(account1.getAccountNumber()));

            System.out.println("========================================================");

        } catch (InvalidAmountException | AccountNotFoundException | InsufficientBalanceException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}
