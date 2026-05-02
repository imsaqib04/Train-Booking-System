# 🚆 Train Booking System (Microservices Architecture)

A scalable and production-ready **Train Booking System** built using **Spring Boot Microservices Architecture**. This project demonstrates real-world backend concepts like service discovery, API Gateway, JWT authentication, and inter-service communication.

---

## 📌 Overview

This system allows users to:

* Register & authenticate securely
* Search trains
* Book tickets
* Process payments
* Manage bookings and seat availability

The system is designed using **loosely coupled microservices**, ensuring scalability, maintainability, and fault isolation.

---

## 🏗️ Architecture

This project follows **Microservices Architecture** with service discovery and centralized routing.

### 🔹 Services Included

* **API Gateway**

  * Single entry point for all requests
  * Routes traffic to appropriate services

* **Auth Service**

  * Handles authentication & authorization
  * Generates JWT tokens

* **User Service**

  * Manages user data and profiles

* **Train Service**

  * Manages train details and seat availability

* **Booking Service**

  * Handles ticket booking
  * Booking status: `CONFIRMED`, `WAITING`, `CANCELLED`

* **Payment Service**

  * Handles payment processing
  * Integrates with booking flow

* **Discovery Server (Eureka)**

  * Service registration & discovery
  * Enables dynamic communication between services

---

## ⚙️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Cloud Gateway
* Eureka Discovery Server
* OpenFeign (Inter-service communication)

### Database

* MySQL (Database per service)

### Tools

* Maven
* Postman
* Git & GitHub

---

## 🔐 Security

* JWT-based Authentication
* Secure API endpoints
* Token validation via Gateway
* Stateless session management

---

## 🔄 Inter-Service Communication

* Feign Clients used for service-to-service communication
* Loose coupling between services
* Discovery Server enables dynamic service lookup

---

## 📦 Features

* ✅ User Registration & Login
* ✅ JWT Authentication & Authorization
* ✅ Train Search & Management
* ✅ Seat Availability Check
* ✅ Ticket Booking (with constraints)
* ✅ Payment Integration
* ✅ Booking Status Handling
* ✅ Service Discovery (Eureka)
* ✅ API Gateway Routing

---

## 📁 Project Structure

```
Train-Booking-System/
│
├── Api-Gateway/
├── Auth-Service/
├── User-Service/
├── Train-Service/
├── Booking-Service/
├── Payment-Service/
├── Discovery-Server/
└── README.md
```

---

## 🚀 How to Run the Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/imsaqib04/Train-Booking-System.git
cd Train-Booking-System
```

---

### 2️⃣ Start Services (Order Important ⚠️)

Start services in this order:

1. **Discovery Server**
2. **API Gateway**
3. Auth Service
4. User Service
5. Train Service
6. Booking Service
7. Payment Service

---

### 3️⃣ Run Each Service

```bash
mvn spring-boot:run
```

Or run via IDE (IntelliJ / Eclipse)

---

## 🔗 System Flow

```
Client → API Gateway → Microservices
                  ↓
        -----------------------
        |  Auth Service       |
        |  User Service       |
        |  Train Service      |
        |  Booking Service    |
        |  Payment Service    |
        -----------------------
                  ↑
         Discovery Server (Eureka)
```

# ScreenShots
<img width="1890" height="951" alt="image" src="https://github.com/user-attachments/assets/86471617-b6ad-437c-8ca8-9115000c2386" />

---

## 🧠 Key Concepts Implemented

* Microservices Architecture
* API Gateway Pattern
* Service Discovery (Eureka)
* JWT Authentication
* Inter-Service Communication (Feign)
* Database per Service
* Scalable Backend Design

---

## 🚀 Future Enhancements

* 🔹 Docker & Docker Compose
* 🔹 Kafka / RabbitMQ (Async Communication)
* 🔹 Circuit Breaker (Resilience4j)
* 🔹 Centralized Config Server
* 🔹 Swagger API Documentation
* 🔹 Role-Based Access Control (RBAC)

---

## 👨‍💻 Author

**Mohd Saqib**
Backend Developer (Spring Boot | Django | Microservices)

---

## ⭐ Show Your Support

If you like this project:

* ⭐ Star this repository
* 🍴 Fork it
* 📢 Share with others



