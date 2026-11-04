package com.complaint.model;

public class Complaint {
	private int id;
    private int userId;
    private int categoryId;
    
    private String title;
    private String description;
    private String status;
    private String adminResponse;
    
    public Complaint() {
    }
    
    public Complaint(int id, int userId, int categoryId, String title, String description,
    		                          String status, String adminResponse) {
    	this.id = id;
    	this.userId = userId;
    	this.categoryId = categoryId;
    	this.title = title;
    	this.description = description;
    	this.status = status;
    	this.adminResponse = adminResponse;
    }
    
      public int getId() {
    	  return id;
      }
      
      public void setId(int id) {
    	  this.id = id;
      }
      
      public int getUserId() {
    	  return userId;
      }
      
      public void setUserId(int userId) {
    	  this.userId = userId;
      }
      
      public int getCategoryId() {
    	  return categoryId;
      }
      
      public void setCategoryId(int categoryId) {
    	  this.categoryId = categoryId;
      }
      
      public String getTitle() {
    	  return title;
      }
      
      public void setTitle(String title) {
    	  this.title = title;
      }
      
      public String getDescription() {
    	  return description;
      }
      
      public void setDescription(String description) {
    	  this.description = description;
      }
      
      public String getStatus() {
    	  return status;
      }
      
      public void setStatus(String status) {
    	  this.status = status;
      }
      
      public String getAdminResponse() {
    	  return adminResponse;
      }
      
      public void setAdminResponse(String adminResponse) {
    	  this.adminResponse = adminResponse;
      }
}
