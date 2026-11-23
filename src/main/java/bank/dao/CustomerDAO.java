
package bank.dao;
import bank.model.Customer;
import java.sql.ResultSet;
import bank.model.IndividualCustomer;
import bank.model.CompanyCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {

    // Save an individual customer
    public void saveIndividual(IndividualCustomer customer) throws SQLException {

        String sql = "INSERT INTO customers " +
                "(customer_id, customer_type, first_name, surname, id_number, employed, address, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, "INDIVIDUAL");
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getSurname());
            stmt.setString(5, customer.getIdNumber());
            stmt.setBoolean(6, customer.isEmployed());
            stmt.setString(7, customer.getAddress());
            stmt.setString(8, customer.getPhone());
            stmt.setString(9, customer.getEmail());

            stmt.executeUpdate();
        }
    }

    // Save a company customer
    public void saveCompany(CompanyCustomer customer) throws SQLException {

        String sql = "INSERT INTO customers " +
                "(customer_id, customer_type, company_name, registration_number, address, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, "COMPANY");
            stmt.setString(3, customer.getCompanyName());
            stmt.setString(4, customer.getRegistrationNumber());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getPhone());
            stmt.setString(7, customer.getEmail());

            stmt.executeUpdate();
        }
    }

    // Find a customer using their ID
    public Customer findCustomer(String customerId) throws SQLException {

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String type = rs.getString("customer_type");

                    if (type.equals("INDIVIDUAL")) {
                        return new IndividualCustomer(
                                rs.getString("customer_id"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("surname"),
                                rs.getString("id_number"),
                                rs.getBoolean("employed")
                        );
                    }

                    if (type.equals("COMPANY")) {
                        return new CompanyCustomer(
                                rs.getString("customer_id"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("company_name"),
                                rs.getString("registration_number")
                        );
                    }
                }
            }
        }

        return null;
    }

}
