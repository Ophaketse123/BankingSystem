
package bank.dao;

import bank.model.SavingsAccount;
import bank.model.Transaction;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TestAccountUpdate {

    public static void main(String[] args) {

        SavingsAccount account = new SavingsAccount("S102", "Main Branch");
        AccountDAO dao = new AccountDAO();

        try {
            // Save a new account
            dao.saveAccount("C101", account);

            // Deposit P200
            Transaction transaction = account.deposit(new BigDecimal("200"));

            // Save balance and transaction
            dao.saveBalanceAndTransaction(account, transaction);

            // Check saved information
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(
                    "SELECT balance FROM accounts WHERE account_number = 'S102'");

            if (rs.next()) {
                System.out.println("Saved balance: P" + rs.getBigDecimal("balance"));
            }

            rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM transactions WHERE account_number = 'S102'");

            if (rs.next()) {
                System.out.println("Transactions saved: " + rs.getInt(1));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
