package com.bank.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
public static Connection getConnection(){
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/BankingApplication", "root", "student@25");
			
		} catch (ClassNotFoundException | SQLException e) {
			 
			System.out.println(e.getMessage());
		}
		
		return con;
		
	}

}