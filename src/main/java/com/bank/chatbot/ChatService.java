package com.bank.chatbot;

import java.math.BigDecimal;

import com.bank.model.Account;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;

public class ChatService {

    private String lastAction = null;
    private String tempAccNo = null;
    private String tempToAcc = null;  

    private final AccountService accountService;
    private final TransactionService transactionService;

    public ChatService(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public String processMessage(String msg) {

        msg = msg.toLowerCase();

        try {

           
            if (msg.equals("deposit")) {
                lastAction = "deposit";
                tempAccNo = null;
                return "Enter account number:";
            }

            if (msg.equals("withdraw")) {
                lastAction = "withdraw";
                tempAccNo = null;
                return "Enter account number:";
            }

            if (msg.equals("balance")) {
                lastAction = "balance";
                tempAccNo = null;
                return "Enter account number:";
            }

            if (msg.equals("transfer")) {
                lastAction = "transfer";
                tempAccNo = null;
                tempToAcc = null;
                return "Enter sender account number:";
            }

       
            if ("balance".equals(lastAction)) {
                Account acc = accountService.getAccount(msg);
                reset();
                return "💰 Balance: ₹" + acc.getOpeningBalance();
            }

      
            if (("deposit".equals(lastAction) || "withdraw".equals(lastAction)) && tempAccNo == null) {
                tempAccNo = msg;
                return "Enter amount:";
            }

          
            if ("deposit".equals(lastAction) && tempAccNo != null) {
                BigDecimal amount = new BigDecimal(msg);
                transactionService.deposit(tempAccNo, amount);
                reset();
                return "✅ Deposit successful!";
            }
 
            if ("withdraw".equals(lastAction) && tempAccNo != null) {
                BigDecimal amount = new BigDecimal(msg);
                transactionService.withdraw(tempAccNo, amount);
                reset();
                return "✅ Withdraw successful!";
            }

 
            if ("transfer".equals(lastAction) && tempAccNo == null) {
                tempAccNo = msg;
                return "Enter receiver account number:";
            }

         
            if ("transfer".equals(lastAction) && tempToAcc == null) {
                tempToAcc = msg;
                return "Enter amount:";
            }

   
            if ("transfer".equals(lastAction)) {
                BigDecimal amount = new BigDecimal(msg);
                transactionService.transfer(tempAccNo, tempToAcc, amount);
                reset();
                return "✅ Transfer successful!";
            }

        } catch (Exception e) {
            reset();
            return "❌ " + e.getMessage();
        }

        return "Type: deposit / withdraw / transfer / balance";
    }

    public void reset() {
        lastAction = null;
        tempAccNo = null;
        tempToAcc = null;
    }
}