
package bank.dao;

public class TestUserDAO {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        // Temporary password for testing only
        String password = "TestBank2026!";

        try {
            dao.saveUser("testuser01", password, "C101");

            System.out.println("User saved successfully!");

            boolean correct = dao.checkLogin("testuser01", password);

            if (correct) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid login details.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
