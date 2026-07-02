# 🏦 Banking Simulator

A full-stack Java banking application that simulates core banking operations — account creation, deposits, withdrawals, fund transfers, transaction history, and user authentication — with a REST API backend and a simple web-based frontend. Includes email alerts for transactions and a built-in chatbot assistant.

## ✨ Features

- **User Authentication** — Register and login
- **Account Management** — Create accounts, view account details, list all accounts
- **Transactions** — Deposit, withdraw, and transfer money between accounts
- **Email Alerts** — Automated email notifications on transactions
- **Transaction Reports** — Generates downloadable transaction reports
- **Chatbot Assistant** — In-app chatbot to help users with banking queries
- **Custom Exception Handling** — Dedicated exceptions for invalid amounts, insufficient balance, and account-not-found scenarios

## 🛠️ Tech Stack

**Backend**
- Java
- [Spark Java](https://sparkjava.com/) — lightweight REST API framework
- MySQL (via `mysql-connector-j`)
- Jakarta Mail — for sending email alerts
- Gson — JSON serialization/deserialization
- Maven — build & dependency management

**Frontend**
- HTML, CSS, JavaScript

## 📁 Project Structure

```
Banking-Application/
├── frontend/                     # Web UI (HTML, CSS, JS)
├── reports/                      # Generated transaction reports
├── src/main/java/com/bank/
│   ├── ApiServer.java             # Main entry point — starts REST API server
│   ├── chatbot/                   # Chatbot request/response handling
│   ├── exception/                 # Custom exceptions
│   ├── model/                     # Account & User models
│   ├── repository/                # DB access layer (JDBC)
│   ├── service/                   # Business logic (accounts, auth, transactions, alerts)
│   └── util/                      # Email & file report utilities
├── pom.xml                       # Maven configuration
└── MIT_License
```

## ⚙️ Prerequisites

- Java JDK 8+
- Maven
- MySQL Server

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/riyasonar25/Banking-Application.git
cd Banking-Application
```

### 2. Set up the database
Create a MySQL database named `BankingApplication`:
```sql
CREATE DATABASE BankingApplication;
```

Update your database connection details (host, port, username, password) in:
```
src/main/java/com/bank/repository/DBConnection.java
```

### 3. Build the project
```bash
mvn clean install
```

### 4. Run the backend server
```bash
mvn exec:java -Dexec.mainClass="com.bank.ApiServer"
```
The API server starts on **`http://localhost:8082`**.

### 5. Launch the frontend
Open `frontend/index.html` in your browser.

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | User login |
| POST | `/accounts/create` | Create a new account |
| GET | `/accounts/all` | Get all accounts |
| GET | `/accounts/:accNo` | Get account details by account number |
| POST | `/transactions/deposit` | Deposit money |
| POST | `/transactions/withdraw` | Withdraw money |
| POST | `/transactions/transfer` | Transfer money between accounts |
| POST | `/chat` | Send a message to the chatbot |
| POST | `/chat/reset` | Reset chatbot conversation |

## 📄 License

This project is licensed under the [MIT License](MIT_License).

## 👤 Author

**Riya Sonar**
