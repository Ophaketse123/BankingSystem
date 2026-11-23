
package bank.dao;

import bank.model.Transaction;
import bank.model.TransactionType;
import java.math.BigDecimal;
import java.sql.SQLException;

public class TestTransactionDAO {

    public static void main(String[] args) {

        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                new BigDecimal("300"),
                new BigDecimal("300"),
                "Savings account deposit"
        );

        TransactionDAO dao = new TransactionDAO();

        try {
            dao.saveTransaction("S101", transaction);
            System.out.println("Transaction saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
