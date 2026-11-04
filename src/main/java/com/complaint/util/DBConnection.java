package com.complaint.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/complaint_management";

    private static final String USER = "root";

    private static final String PASSWORD = "Aakash@2004";

    public static Connection getConnection() {

        Connection connection = null;

        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             System.out.println("MySQL JDBC driver loaded successfully!");

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database connected successfully!");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return connection;
    }
}