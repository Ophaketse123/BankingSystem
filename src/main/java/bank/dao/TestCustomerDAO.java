
package bank.dao;

import bank.model.IndividualCustomer;
import java.sql.SQLException;

public class TestCustomerDAO {

    public static void main(String[] args) {

        IndividualCustomer customer = new IndividualCustomer(
                "C101",
                "Gaborone",
                "71234567",
                "neo@example.com",
                "Neo",
                "Moyo",
                "123456789",
                true
        );

        CustomerDAO dao = new CustomerDAO();

        try {
            dao.saveIndividual(customer);
            System.out.println("Customer saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
