
package bank.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestReadCustomers {

    public static void main(String[] args) {

        String sql = "SELECT customer_id, first_name, surname FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Customer ID: " + rs.getString("customer_id"));
                System.out.println("Name: " + rs.getString("first_name"));
                System.out.println("Surname: " + rs.getString("surname"));
                System.out.println("----------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
