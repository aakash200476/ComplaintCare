# 🏢 ComplaintCare – Online Complaint Management System

A Java-based web application designed to simplify and streamline the complaint registration and management process. ComplaintCare provides a centralized platform where users can submit complaints, track their progress, and receive responses from administrators.

The system provides separate functionalities for users and administrators, making complaint handling more organized, transparent, and efficient.

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Objectives](#-objectives)
- [Key Features](#-key-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Database Design](#-database-design)
- [Application Workflow](#-application-workflow)
- [Team Members](#-team-members)
---

## 📖 Project Overview

ComplaintCare is an Online Complaint Management System developed to provide an easy and efficient way to manage complaints digitally.

Traditionally, complaint registration and resolution processes may involve paperwork, manual tracking, and communication delays. ComplaintCare aims to reduce these difficulties by providing an online platform.

Users can register, log in, submit complaints, check complaint statuses, and view administrator responses. Administrators can access submitted complaints, manage their status, and provide appropriate responses.

The application uses Java Servlets and JDBC for backend processing and MySQL for database management.

---

## 🎯 Objectives

- Develop a centralized online complaint management platform.
- Provide easy complaint registration for users.
- Allow users to track complaint progress.
- Enable administrators to manage complaints efficiently.
- Maintain complaint records in a MySQL database.
- Improve transparency between users and administrators.
- Reduce manual complaint management efforts.

---

## 🚀 Key Features

### 👤 1. User Module

- User Registration
- User Login and Logout
- User Dashboard
- View Profile
- Submit New Complaints
- Select Complaint Categories
- View My Complaints
- Track Complaint Status
- View Administrator Responses

### 🛡️ 2. Admin Module

- Admin Login
- Admin Dashboard
- View All Submitted Complaints
- Manage User Complaints
- Update Complaint Status
- Provide Responses to Complaints
- Monitor Complaint Progress

### ⚙️ 3. General Features

- Role-Based Access Control
- JDBC Database Connectivity
- PreparedStatement for SQL Queries
- Dynamic Data Retrieval
- Separate User and Admin Dashboards
- User-Friendly Interface
- Password hashing for security

---

## 🛠️ Technology Stack

| Technology | Purpose |

| Java 21 - Backend Programming |
| Java Servlets - Server-Side Request Handling |
| JDBC - Database Connectivity |
| MySQL - Relational Database |
| HTML5 - Frontend Structure |
| CSS3 - Styling and Layout |
| JavaScript - Frontend Functionality |
| Apache Tomcat 10.1 - Web Application Server |
| Eclipse IDE - Development Environment |
| Git - Version Control |
| GitHub - Source Code Management |
| GitHub Desktop - Repository Management |

### Libraries and Dependencies

- MySQL Connector/J – Database connectivity.
- jbcrypt - password hashing
  
---



## 📂 Project Structure

ComplaintCare/
│
├── src/
│   └── main/
│         └── java/
│               └── com/
│                    └── complaint/
│                         │
│                         ├── controller/
│                         │   ├── LoginServlet.java
│                         │   ├── RegisterServlet.java
│                         │   ├── LogoutServlet.java
│                         │   ├── SubmitComplaintServlet.java
│                         │   ├── MyComplaintServlet.java
│                         │   ├── AdminDashboardServlet.java
│                         │   ├── AdminStatisticsServlet.java
│                         │   ├── DeleteComplaintServlet.java
│                         │   ├── UpdateAdminResponseServlet.java
│                         │   ├── UpdateComplaintStatusServlet.java
│                         │   ├── ViewProfileServlet.java
│                         │   └── CurrentUserNameServlet.java
│                         │
│                         ├── dao/
│                         │   ├── UserDAO.java
│                         │   ├── ComplaintDAO.java
│                         │   └── TestUserDAO.java
│                         │
│                         ├── model/
│                         │   ├── User.java
│                         │   ├── Complaint.java
│                         │   └── Category.java
│                         │
│                         └── util/
│                         │   ├── TestConnection.java
│                         │   └── DBConnection.java
│
├── WebContent/
│   │
│   ├── css/
│   │   ├── index.css
│   │   ├── login.css
│   │   ├── register.css
│   │   ├── mycomplaints.css
│   │   ├── userdashboard.css
│   │   ├── admindashboard.css
│   │   ├── submitcomplaint.css
│   │   └── viewprofile.css
│   │
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── user-dashboard.html
│   ├── admin-dashboard.html
│   ├── my-complaints.html
│   ├── submit-complaint.html
│   └── view-profile.html
│
├── WEB-INF/
│   ├── lib/
│   │   ├── mysql-connector-j.jar
│   │   └── jbcrypt.jar
│   │
│   └── web.xml
│
├── .gitignore
└── README.md

## 🗄️ Database Design

**Database Name:** `complaint_management`

ComplaintCare uses MySQL to store and manage application data.

### Main Database Tables

**1. Users Table**

Stores registered user and administrator information.

| Column | Description |
|---|---|
| id | Unique user ID |
| name | User's full name |
| email | User's email address |
| password | User's password/hash |
| role | User role (user/admin) |
| created_at | date & time of creation |

**2. Complaints Table**

Stores complaints submitted by users.

| Column | Description |
|---|---|
| id | Unique complaint ID |
| user_id | Reference to the user |
| category_id | Complaint category |
| title | Complaint title |
| description | Complaint description |
| status | Current complaint status |
| admin_response | Administrator's response |
| created_at | date & time of creation |
| updated_at | date & time of updation |

**3. Complaint Categories Table**

Stores different complaint categories used during complaint registration.

### Create Database

Execute the following SQL command in MySQL Workbench:

CREATE DATABASE complaint_management;

USE complaint_management;

Create the required tables using your project's database schema.

---

## 🔄 Application Workflow

### User Workflow

1. User visits the ComplaintCare website.
2. User creates an account through registration.
3. User logs in using registered credentials.
4. User accesses the user dashboard.
5. User submits a complaint with the required details.
6. Complaint information is saved in the database.
7. User navigates to My Complaints.
8. User checks complaint status and administrator responses.

### Admin Workflow

1. Administrator logs in using admin credentials.
2. Administrator accesses the admin dashboard.
3. Administrator views submitted complaints.
4. Administrator reviews complaint details.
5. Administrator updates complaint status.
6. Administrator provides a response.
7. Updated information becomes available to the user.

---
## 👥 Team Members

ComplaintCare is developed collaboratively by two team members. Responsibilities are divided into frontend and backend development to ensure organized development and efficient project execution.

### 1. Aakash Verma

**Role: Backend Developer**

**Responsibilities:**
- Backend development using Java.
- Implementation of Java Servlets.
- Database connectivity using JDBC.
- MySQL database design and management.
- Development of DAO and Model classes.
- User registration and authentication.
- Session management and role-based access.
- Complaint submission and retrieval.
- Admin dashboard functionality.
- Complaint status updates and admin responses.
- Backend testing and debugging.

### 2. [Payal Patidar]

**Role: Frontend Developer**

**Responsibilities:**
- Frontend development using HTML5.
- Designing user interfaces using CSS3.
- JavaScript functionality and interactions.
- Designing registration and login pages.
- Developing user and admin dashboards.
- Designing complaint submission forms.
- Designing complaint listing and profile pages.
- Maintaining consistent styling across pages.
- Responsive design and user experience.
---


**Developed with ❤️ using Java, MySQL, HTML, CSS, and JavaScript.**
