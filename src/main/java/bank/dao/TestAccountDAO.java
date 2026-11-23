
package bank.dao;

import bank.model.SavingsAccount;
import java.math.BigDecimal;
import java.sql.SQLException;

public class TestAccountDAO {

    public static void main(String[] args) {

        SavingsAccount account = new SavingsAccount("S101", "Main Branch");

        account.deposit(new BigDecimal("300"));

        AccountDAO dao = new AccountDAO();

        try {
            dao.saveAccount("C101", account);
            System.out.println("Account saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
