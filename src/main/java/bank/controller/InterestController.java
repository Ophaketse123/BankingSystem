
package bank.controller;

import bank.dao.InterestDAO;
import java.math.BigDecimal;
import java.sql.SQLException;

public class InterestController {

    public BigDecimal payInterest(String accountNumber)
            throws SQLException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Enter account number.");
        }

        InterestDAO dao = new InterestDAO();

        return dao.payMonthlyInterest(accountNumber.trim());
    }
}
