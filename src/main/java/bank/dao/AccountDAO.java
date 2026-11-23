
package bank.dao;

import bank.model.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class AccountDAO {

    // Save a new account
    public void saveAccount(String customerId, Account account)
            throws SQLException {

        String accountType;

        if (account instanceof SavingsAccount) {
            accountType = "SAVINGS";
        } else if (account instanceof InvestmentAccount) {
            accountType = "INVESTMENT";
        } else if (account instanceof ChequeAccount) {
            accountType = "CHEQUE";
        } else {
            throw new IllegalArgumentException("Invalid account type.");
        }

        String sql = "INSERT INTO accounts " +
                "(account_number, customer_id, account_type, branch, balance, open_date, employer_name, employer_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";



        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, customerId);
            stmt.setString(3, accountType);
            stmt.setString(4, account.getBranch());
            stmt.setBigDecimal(5, account.getBalance());
            stmt.setDate(6, Date.valueOf(account.getOpenDate()));

            if (account instanceof ChequeAccount) {
                ChequeAccount cheque = (ChequeAccount) account;

                stmt.setString(7, cheque.getEmployer().getEmployerName());
                stmt.setString(8, cheque.getEmployer().getEmployerAddress());

            } else {
                stmt.setString(7, null);
                stmt.setString(8, null);
            }

            stmt.executeUpdate();
        }
    }

    // Update balance and save transaction
    public void saveBalanceAndTransaction(Account account,
                                          Transaction transaction)
            throws SQLException {

        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setBigDecimal(1, account.getBalance());
                    stmt.setString(2, account.getAccountNumber());

                    int rows = stmt.executeUpdate();

                    if (rows == 0) {
                        throw new SQLException("Account not found.");
                    }
                }

                TransactionDAO dao = new TransactionDAO();
                dao.saveTransaction(conn, account.getAccountNumber(), transaction);

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Read saved balance
    public BigDecimal getSavedBalance(String accountNumber)
            throws SQLException {

        String sql = "SELECT balance FROM accounts WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        }

        throw new SQLException("Account not found.");
    }

    // Deposit money and save it in H2
    public BigDecimal depositToDatabase(String accountNumber, BigDecimal amount)
            throws SQLException {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid deposit amount.");
        }

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                String sql = "UPDATE accounts SET balance = balance + ? " +
                        "WHERE account_number = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setBigDecimal(1, amount);
                    stmt.setString(2, accountNumber);

                    if (stmt.executeUpdate() == 0) {
                        throw new SQLException("Account not found.");
                    }
                }

                String balanceSql = "SELECT balance FROM accounts WHERE account_number = ?";

                BigDecimal newBalance;

                try (PreparedStatement stmt = conn.prepareStatement(balanceSql)) {
                    stmt.setString(1, accountNumber);

                    try (ResultSet rs = stmt.executeQuery()) {
                        rs.next();
                        newBalance = rs.getBigDecimal("balance");
                    }
                }

                Transaction transaction = new Transaction(
                        TransactionType.DEPOSIT, amount, newBalance, "Cash deposit");

                TransactionDAO dao = new TransactionDAO();
                dao.saveTransaction(conn, accountNumber, transaction);

                conn.commit();
                return newBalance;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Withdraw money from the database
    public BigDecimal withdrawFromDatabase(String accountNumber, BigDecimal amount)
            throws SQLException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Enter account number.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid amount.");
        }

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                String sql = "SELECT account_type, balance FROM accounts " +
                        "WHERE account_number = ? FOR UPDATE";

                BigDecimal balance;
                String accountType;

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, accountNumber.trim());

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Account not found.");
                        }

                        accountType = rs.getString("account_type");
                        balance = rs.getBigDecimal("balance");
                    }
                }

                if (accountType.equals("SAVINGS")) {
                    throw new IllegalArgumentException(
                            "Savings accounts cannot withdraw money.");
                }

                if (!accountType.equals("INVESTMENT") &&
                        !accountType.equals("CHEQUE")) {
                    throw new IllegalArgumentException("Invalid account type.");
                }

                if (amount.compareTo(balance) > 0) {
                    throw new IllegalArgumentException("Insufficient balance.");
                }

                BigDecimal newBalance = balance.subtract(amount);

                String updateSql = "UPDATE accounts SET balance = ? " +
                        "WHERE account_number = ?";

                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setBigDecimal(1, newBalance);
                    stmt.setString(2, accountNumber.trim());
                    stmt.executeUpdate();
                }

                Transaction transaction = new Transaction(
                        TransactionType.WITHDRAWAL,
                        amount, newBalance, "Cash withdrawal");

                TransactionDAO dao = new TransactionDAO();
                dao.saveTransaction(conn, accountNumber.trim(), transaction);

                conn.commit();
                return newBalance;

            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }


    // Check whether an account belongs to a customer
    public boolean customerOwnsAccount(String customerId, String accountNumber)
            throws SQLException {

        if (customerId == null || accountNumber == null) {
            return false;
        }

        String sql = "SELECT account_number FROM accounts " +
                "WHERE customer_id = ? AND account_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            stmt.setString(2, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

}
