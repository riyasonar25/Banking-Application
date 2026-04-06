package com.bank;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;

import java.math.BigDecimal;

import com.bank.chatbot.ChatRequest;
import com.bank.chatbot.ChatService;
import com.bank.chatbot.ChatResponse;
import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;
import com.bank.service.AlertService;
import com.bank.service.AuthService;
import com.bank.service.TransactionService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ApiServer {
	

	public static void main(String[] args) {
		
		port(8082);
		enableCORS();
		Gson gson = new Gson();
		
		AccountRepository accRepo = new AccountRepository();
		AccountService accService = new AccountService(accRepo);
		TransactionRepository trxRepo = new TransactionRepository();
		AlertService alertService = new AlertService(new BigDecimal("1000"));
		TransactionService trxService = new TransactionService(accService, trxRepo, alertService);
		
		AuthService authService = new AuthService();
		ChatService chatService = new ChatService(accService, trxService);
		
		post("/accounts/create",(req,res) -> {
			res.type("application/json");
			System.out.println("/accounts/create API Called.." );
			AccountRequest data = gson.fromJson(req.body(),AccountRequest.class);
			Account acc = accService.createAccount(data.name, data.email, data.balance);
			return gson.toJson(acc);
			
		});
		
		 post("/transactions/deposit", (req, res) -> {
	            System.out.println("/transactions/deposit api is called");

	            TxRequest data = gson.fromJson(req.body(), TxRequest.class);
	            trxService.deposit(data.accNo, data.amount);

	            return "Deposit successfully..!";
	        });
		
		post("/transactions/withdraw",(req,res) ->{
			System.out.println("/tranctions/withdraw API Called.." );
			TxRequest data = gson.fromJson(req.body(), TxRequest.class);
			trxService.withdraw(data.accNo, data.amount);
			return "Withdraw Successfully..!";
		});
		
		post("/transactions/transfer",(req,res) ->{
			System.out.println("/tranctions/transfer API Called.." );
			TransferRequest data = gson.fromJson(req.body(), TransferRequest.class);
			trxService.transfer(data.fromAcc, data.toAcc,data.amount);
			return "Transfer Transaction Successfull..!";
		});
		
		get("/accounts/all",(req,res) -> {
			System.out.println("/accounts/all API is called");
			res.type("application/json");
			
			return gson.toJson(accService.listAllAccounts());
		});
		
		get("/accounts/:accNo",(req,res) -> {
			String accNo = req.params("accNo");
			Account account = accService.getAccount(accNo);
			return gson.toJson(account);
		});
		
		 post("/auth/register", (req, res) -> {
	            User user = gson.fromJson(req.body(), User.class);
	            authService.register(user);
	            return "Registered successfully";
	        });

	        post("/auth/login", (req, res) -> {
	            LoginRequest data = gson.fromJson(req.body(), LoginRequest.class);
	            User user = authService.login(data.email, data.password);
	            return gson.toJson(user);
	        });
	        
	        
	        post("/chat", (req, res) -> {
	            res.type("application/json");

	            ChatRequest request = gson.fromJson(req.body(), ChatRequest.class);
	            String reply = chatService.processMessage(request.message);

	            return gson.toJson(new ChatResponse(reply));
	        });
		
	        post("/chat/reset", (req, res) -> {
	            chatService.reset();
	            return "Chat reset successful!";
	        });
	}
	
	public static void enableCORS() {
		options("/*",(request,response) -> {
			String reqheaders = request.headers("Access-Control-Request-Headers");
			if(reqheaders != null) {
				response.header("Access-Control-Allow-Headers", reqheaders);
			}
			
			String reqMethod = request.headers("Access-Control-Request-Method");
			if(reqMethod != null) {
				response.header("Access-Control-Allow-Methods", reqMethod);
			}
			
			return "OK";
			
		});
		
		before((req,res) -> {
			res.header("Access-Control-Allow-Origin","*");
			res.header("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
			res.header("Access-Control-Allow-Headers","Content-Type,Authorization");
		});
		
	}
	
	static class AccountRequest{
		String name;
		String email;
		BigDecimal balance;
	}
	
	static class TxRequest{
		String accNo;
		BigDecimal amount;
	}
	
	static class TransferRequest{
		String fromAcc;
		String toAcc;
		BigDecimal amount;
		
	}
	
	static class LoginRequest{
		String email;
		String password;
		
	}

}



















