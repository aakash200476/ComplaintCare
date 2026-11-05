package com.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.complaint.model.User;
import com.complaint.util.DBConnection;

public class UserDAO {

    public User login(String email, String password) {

        User user = null;

       /* String sql = "SELECT * FROM users WHERE email = ? AND password = ?";*/
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, email);
           /* ps.setString(2, password); */

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	
            	String storedHash = rs.getString("password");
            	
            	if(BCrypt.checkpw(password,storedHash)) {

                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
               /* user.setPassword(rs.getString("password")); */
                user.setPassword(storedHash);
                user.setRole(rs.getString("role"));
            }

            rs.close();
            ps.close();
            connection.close();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }
    

public boolean register(String name, String email, String password) {

    String sql = "INSERT INTO users (name, email, password, role) "
               + "VALUES (?, ?, ?, 'USER')";

    try (java.sql.Connection connection =
                 com.complaint.util.DBConnection.getConnection()) {

        if (connection == null) {
            return false;
        }

        try (java.sql.PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            return ps.executeUpdate() == 1;
        }

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        // Email already exists.
        return false;
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}