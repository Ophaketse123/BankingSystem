
package bank.dao;

import bank.model.Transaction;
import bank.model.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDate;

public class InterestDAO {

    public BigDecimal payMonthlyInterest(String accountNumber)
            throws SQLException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Enter account number.");
        }

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                String sql = "SELECT account_type, balance FROM accounts " +
                        "WHERE account_number = ? FOR UPDATE";

                String type;
                BigDecimal balance;

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, accountNumber);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Account not found.");
                        }

                        type = rs.getString("account_type");
                        balance = rs.getBigDecimal("balance");
                    }
                }

                BigDecimal rate;

                if (type.equals("SAVINGS")) {
                    rate = new BigDecimal("0.0005");
                } else if (type.equals("INVESTMENT")) {
                    rate = new BigDecimal("0.05");
                } else {
                    throw new IllegalArgumentException(
                            "This account does not earn interest.");
                }

                LocalDate firstDay = LocalDate.now().withDayOfMonth(1);

                String checkSql = "SELECT COUNT(*) FROM transactions " +
                        "WHERE account_number = ? AND transaction_type = 'INTEREST' " +
                        "AND date_time >= ? AND date_time < ?";

                try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
                    stmt.setString(1, accountNumber);
                    stmt.setTimestamp(2, Timestamp.valueOf(firstDay.atStartOfDay()));
                    stmt.setTimestamp(3, Timestamp.valueOf(firstDay.plusMonths(1).atStartOfDay()));

                    try (ResultSet rs = stmt.executeQuery()) {
                        rs.next();

                        if (rs.getInt(1) > 0) {
                            throw new IllegalArgumentException(
                                    "Interest already paid this month.");
                        }
                    }
                }

                BigDecimal interest = balance.multiply(rate)
                        .setScale(2, RoundingMode.HALF_UP);

                if (interest.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("No interest to pay.");
                }

                BigDecimal newBalance = balance.add(interest);

                String updateSql = "UPDATE accounts SET balance = ? " +
                        "WHERE account_number = ?";

                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setBigDecimal(1, newBalance);
                    stmt.setString(2, accountNumber);
                    stmt.executeUpdate();
                }

                Transaction transaction = new Transaction(
                        TransactionType.INTEREST,
                        interest, newBalance, "Monthly interest");

                TransactionDAO dao = new TransactionDAO();
                dao.saveTransaction(conn, accountNumber, transaction);

                conn.commit();
                return interest;

            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
