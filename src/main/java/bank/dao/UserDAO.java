
package bank.dao;

import java.sql.*;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserDAO {

    // Save a new user
    public void saveUser(String username, String password,
                         String customerId) throws Exception {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            throw new IllegalArgumentException("Enter username and password.");
        }

        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);

        String salt = Base64.getEncoder().encodeToString(saltBytes);
        String hash = hashPassword(password, salt);

        String sql = "INSERT INTO users " +
                "(username, password_hash, salt, role, customer_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, hash);
            stmt.setString(3, salt);
            stmt.setString(4, "CUSTOMER");
            stmt.setString(5, customerId);

            stmt.executeUpdate();
        }
    }

    // Check login details
    public boolean checkLogin(String username, String password)
            throws Exception {

        if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {
            return false;
        }

        String sql = "SELECT password_hash, salt FROM users " +
                "WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String savedHash = rs.getString("password_hash");
                    String salt = rs.getString("salt");

                    String enteredHash = hashPassword(password, salt);

                    return MessageDigest.isEqual(
                            Base64.getDecoder().decode(savedHash),
                            Base64.getDecoder().decode(enteredHash)
                    );
                }
            }
        }

        return false;
    }

    // Convert a password into a secure hash
    private String hashPassword(String password, String salt)
            throws Exception {

        byte[] saltBytes = Base64.getDecoder().decode(salt);

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), saltBytes, 120000, 256);

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        } finally {
            spec.clearPassword();
        }
    }

    // Find the customer linked to a username
    public String getCustomerId(String username) throws SQLException {

        String sql = "SELECT customer_id FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("customer_id");
                }
            }
        }

        return null;
    }

}
