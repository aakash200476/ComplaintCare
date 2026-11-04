package com.complaint.dao;

import com.complaint.model.User;

public class TestUserDAO{
	public static void main(String[] args) {
		
		UserDAO userDAO = new UserDAO();
		
		User user = userDAO.login("aakash@gmail.com", "123456");
		
		if (user != null) {
			System.out.println("Login successfully");
			System.out.println("Name : " + user.getName());
			System.out.println("Email : " + user.getEmail());
			System.out.println("Role : " + user.getRole());
		}
		else {
			System.out.println("Invalid email or password!");
		}
	}
}