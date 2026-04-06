package com.bank.repository;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.model.User;

public class UserRepository {

    public void register(User user) throws SQLException  {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
    }
   

    public User login(String email, String password) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            return u;
        }
        return null;
    }
}
