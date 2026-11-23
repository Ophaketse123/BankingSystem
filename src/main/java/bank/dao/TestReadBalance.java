
package bank.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TestReadBalance {

    public static void main(String[] args) {

        AccountDAO dao = new AccountDAO();

        try {
            BigDecimal balance = dao.getSavedBalance("S102");

            System.out.println("Saved balance: P" + balance);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
