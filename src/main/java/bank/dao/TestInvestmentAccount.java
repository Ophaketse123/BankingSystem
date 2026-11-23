
package bank.dao;

import bank.model.InvestmentAccount;
import java.math.BigDecimal;
import java.sql.SQLException;

public class TestInvestmentAccount {

    public static void main(String[] args) {

        InvestmentAccount account = new InvestmentAccount(
                "I102", "Main Branch", new BigDecimal("500"));

        AccountDAO dao = new AccountDAO();

        try {
            dao.saveAccount("C101", account);
            System.out.println("Investment account saved!");
            System.out.println("Balance: P" + dao.getSavedBalance("I102"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
