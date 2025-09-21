# 🧘‍♀ Hela Suwaya - Sri Lanka Ayurwedha Wellness Retreat Booking Platform

*Hela Suwaya* is a complete retreat and wellness center booking and management platform.  
It provides an *admin panel* to manage doctors, retreats, packages, wellness programmes, accommodations, payments, bookings, reviews, articles, and dashboards with visual reports.

---

## 📌 Project Description

The purpose of this project is to create a *web-based wellness retreat booking management system* that allows administrators to:

- Manage *treatment packages, doctors, wellness programmes, and users*.  
- Track *bookings, payments, and reviews* all in one place.  
- View *interactive dashboards and charts* for revenue, booking trends, and feedback.  
- Support *image uploads, searching, filtering, and CRUD operations* for all management modules.  
- Provide a seamless booking experience, ensuring effective management of retreats and wellness programs.

**Tech Stack:**

- **Backend:** Spring Boot (REST API), JPA/Hibernate, MySQL  
- **Frontend:** HTML, CSS, Bootstrap, jQuery, Chart.js  
- **Auth:** JWT-based authentication for secure API access  

---

## 🖼 Screenshots

### 🔹 Dashboard Overview
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/7268ccc7-6af6-4d4c-9338-d98a54a46eac" />


### 🔹 Package Management
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/2c3ade50-9f9e-4f68-945c-dd86fa9ac936" />


### 🔹 Doctor Management
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/d4251e2b-8aa1-4b99-a562-957753bb8a16" />


### 🔹 Booking Management
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/f8f5ed43-45ae-4581-bcce-4037c13b9600" />


### 🔹 Email and Notifications
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/0b688f7a-326b-4ef4-a612-2210efccfb49" />




---

## ⚙ Setup Instructions

### ⿡ Clone Repository
```bash
git clone https://github.com/Vindya2004/AWRMS.git
cd AWRMS
⿢ Backend Setup (Spring Boot + MySQL)
Requirements
Java 17+
Maven
MySQL
Steps
Open the backend in an IDE (IntelliJ / Eclipse / VS Code with Java).
Create a new MySQL database:
SQL

CREATE DATABASE awr;
Update application.properties (inside src/main/resources) with your DB credentials:
properties

spring.datasource.url=jdbc:mysql://localhost:3306/awr
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=yourSecretKey
Run backend:
Bash

mvn spring-boot:run
Backend will be available at:
👉 http://localhost:8080/api/v1/...
⿣ Frontend Setup (Admin Panel)
Requirements
Any HTTP server (VS Code Live Server, IntelliJ built-in server, Apache/Nginx, or directly open in a browser).
Modern browser (Chrome/Firefox recommended).
Steps
Navigate to the frontend folder:
Bash

cd frontend
Open admin-panel.html (or index.html) in a browser.
Login with seeded admin credentials (after creating them in DB).
Access Dashboard, Bookings, Packages, Payments, etc.
⿤ Authentication
On login, the backend generates a JWT.
Token is stored in localStorage for the session.
Every request to protected APIs adds the header:
text

Authorization: Bearer <token>

🚀 Features
✅ Retreat package & programmes scheduling
✅ Manage users, doctors, accommodations
✅ Booking management with dynamic price calculation
✅ Payments tracking (card details securely masked)
✅ Article Management
✅ User reviews & feedback management
✅ Dashboard with charts (line chart for trends, doughnut chart for revenue breakdown)
✅ Search & filter across all modules
✅ Image upload and preview support
✅ JWT authentication for secure API access
✅ Responsive UI built with Bootstrap

👨‍💻 Author
  Developed by Vindya Madubhashini

 🎥 Demo Video
🎬 Watch the full walkthrough here:
  [Spring-Boot-Final- project](https://www.youtube.com/watch?v=HmQDJn3IYHM&t=1s)


