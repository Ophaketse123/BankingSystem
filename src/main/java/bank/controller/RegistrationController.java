
package bank.controller;

import bank.dao.CustomerDAO;
import bank.model.IndividualCustomer;

import java.sql.SQLException;
import java.util.UUID;

public class RegistrationController {

    public String registerIndividual(String firstName, String surname,
                                     String idNumber, String address,
                                     String phone, String email,
                                     boolean employed) throws SQLException {

        // Check that all fields are filled
        if (firstName.isBlank() || surname.isBlank() ||
                idNumber.isBlank() || address.isBlank() ||
                phone.isBlank() || email.isBlank()) {

            throw new IllegalArgumentException("Please fill in all fields.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }

        // Generate a customer ID
        String customerId = "C" +
                UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        // Create customer
        IndividualCustomer customer = new IndividualCustomer(
                customerId, address, phone, email,
                firstName, surname, idNumber, employed);

        // Save customer in H2
        CustomerDAO dao = new CustomerDAO();
        dao.saveIndividual(customer);

        return customerId;
    }
}
