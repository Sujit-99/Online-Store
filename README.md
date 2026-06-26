# 🛒 Online Store

An Online Store backend application built using **Java**, **Jakarta Persistence API (JPA)**, and **Hibernate**. This project was created as a learning exercise while exploring Java Enterprise technologies and understanding how a database-driven e-commerce application works.

The project demonstrates CRUD operations, entity relationships, persistence, and a layered architecture using DAO classes.

> **Note:** This is a learning project and was developed to practice Java backend development concepts rather than as a production-ready application.

---

## 🚀 Features

- Product Management
- Category Management
- Customer Management
- Customer Profile Management
- Order Management
- Order Items Management
- Database persistence using JPA/Hibernate
- Layered architecture (Controller → DAO → Entity)
- Entity relationships using JPA annotations

---

## 🛠️ Tech Stack

- Java
- Jakarta Persistence API (JPA)
- Hibernate ORM
- Maven
- MySQL
- IntelliJ IDEA / Eclipse

---

## 📁 Project Structure

```
Online-Store/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/
│   │   │   │   └── OnlineStoreController.java
│   │   │   │
│   │   │   ├── dao/
│   │   │   │   └── OnlineStoreDao.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── Category.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── CustomerProfile.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Orders.java
│   │   │   │   └── OrderItems.java
│   │   │   │
│   │   │   └── ...
│   │   │
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml
│   │
│   └── ...
│
└── README.md
```

---

## 📚 Entity Overview

### Category
Represents product categories such as Electronics, Clothing, etc.

### Product
Stores product information including name, price, stock, and category.

### Customer
Represents customer account information.

### CustomerProfile
Stores additional customer details associated with a customer.

### Orders
Represents an order placed by a customer.

### OrderItems
Represents individual products within an order.

---

## 🗄️ Database Design

The project demonstrates common e-commerce relationships:

- One Category → Many Products
- One Customer → One Customer Profile
- One Customer → Many Orders
- One Order → Many Order Items
- One Product → Many Order Items

---

## ⚙️ Getting Started

### Prerequisites

- Java JDK 17+ (or compatible version)
- Maven
- MySQL
- IDE (IntelliJ IDEA or Eclipse)

### Clone the Repository

```bash
git clone https://github.com/Sujit-99/Online-Store.git
```

### Configure Database

Update the database connection details in:

```
src/main/resources/META-INF/persistence.xml
```

Example:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/online_store"/>
<property name="jakarta.persistence.jdbc.user" value="your_username"/>
<property name="jakarta.persistence.jdbc.password" value="your_password"/>
```

### Run the Project

Import the project into your IDE and run:

```
OnlineStoreController.java
```

---

## 📖 What I Learned

While building this project, I gained practical experience with:

- Java object-oriented programming
- JPA Entity Mapping
- Hibernate ORM
- CRUD Operations
- DAO Design Pattern
- Entity Relationships
- MySQL database integration
- Maven project structure
- Persistence configuration

---

## 👨‍💻 Author

**Sujit**

GitHub: https://github.com/Sujit-99

---
