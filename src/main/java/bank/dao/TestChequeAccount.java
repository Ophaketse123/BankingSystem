
package bank.dao;

import bank.model.ChequeAccount;
import bank.model.Employer;
import java.sql.SQLException;

public class TestChequeAccount {

    public static void main(String[] args) {

        Employer employer = new Employer("ABC Company", "Gaborone");

        ChequeAccount account = new ChequeAccount(
                "CH102", "Main Branch", employer);

        AccountDAO dao = new AccountDAO();

        try {
            dao.saveAccount("C101", account);

            System.out.println("Cheque account saved!");
            System.out.println("Account number: " + account.getAccountNumber());
            System.out.println("Employer: " + employer.getEmployerName());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
