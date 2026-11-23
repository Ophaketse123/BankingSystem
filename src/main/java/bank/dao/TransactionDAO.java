
package bank.dao;
import java.sql.ResultSet;
import bank.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDAO {

    public void saveTransaction(String accountNumber, Transaction transaction)
            throws SQLException {

        try (Connection conn = DBConnection.getConnection()) {
            saveTransaction(conn, accountNumber, transaction);
        }
    }

    public void saveTransaction(Connection conn, String accountNumber,
                                Transaction transaction) throws SQLException {

        String sql = "INSERT INTO transactions " +
                "(account_number, transaction_type, amount, balance_after, description, date_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, transaction.getType().name());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setBigDecimal(4, transaction.getBalanceAfter());
            stmt.setString(5, transaction.getDescription());
            stmt.setTimestamp(6, Timestamp.valueOf(transaction.getDateTime()));

            stmt.executeUpdate();
        }
    }

    // View transactions for an account
    public String getTransactionHistory(String accountNumber)
            throws SQLException {

        String sql = "SELECT * FROM transactions " +
                "WHERE account_number = ? ORDER BY date_time DESC";

        StringBuilder history = new StringBuilder();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    history.append("Type: ")
                            .append(rs.getString("transaction_type"))
                            .append("\nAmount: P")
                            .append(rs.getBigDecimal("amount"))
                            .append("\nDate: ")
                            .append(rs.getTimestamp("date_time"))
                            .append("\nBalance: P")
                            .append(rs.getBigDecimal("balance_after"))
                            .append("\n----------------------\n");
                }
            }
        }

        if (history.length() == 0) {
            return "No transactions found.";
        }

        return history.toString();
    }

}
