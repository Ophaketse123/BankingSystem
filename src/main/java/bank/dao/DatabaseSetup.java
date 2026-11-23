
package bank.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void main(String[] args) {

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Customers table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS customers (
                    customer_id VARCHAR(20) PRIMARY KEY,
                    customer_type VARCHAR(20) NOT NULL,
                    first_name VARCHAR(50),
                    surname VARCHAR(50),
                    id_number VARCHAR(30),
                    employed BOOLEAN,
                    company_name VARCHAR(100),
                    registration_number VARCHAR(30),
                    address VARCHAR(100),
                    phone VARCHAR(20),
                    email VARCHAR(100)
                )
                """);

            // Accounts table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number VARCHAR(20) PRIMARY KEY,
                    customer_id VARCHAR(20) NOT NULL,
                    account_type VARCHAR(20) NOT NULL,
                    branch VARCHAR(50),
                    balance DECIMAL(15,2),
                    open_date DATE,
                    employer_name VARCHAR(100),
                    employer_address VARCHAR(100),
                    FOREIGN KEY (customer_id)
                        REFERENCES customers(customer_id)
                )
                """);

            // Transactions table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    account_number VARCHAR(20) NOT NULL,
                    transaction_type VARCHAR(20),
                    amount DECIMAL(15,2),
                    balance_after DECIMAL(15,2),
                    description VARCHAR(200),
                    date_time TIMESTAMP,
                    FOREIGN KEY (account_number)
                        REFERENCES accounts(account_number)
                )
                """);

            // Users table for login
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    username VARCHAR(50) PRIMARY KEY,
                    password_hash VARCHAR(255) NOT NULL,
                    salt VARCHAR(100) NOT NULL,
                    role VARCHAR(20) NOT NULL,
                    customer_id VARCHAR(20),
                    FOREIGN KEY (customer_id)
                        REFERENCES customers(customer_id)
                )
                """);

            System.out.println("Database tables created successfully!");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
