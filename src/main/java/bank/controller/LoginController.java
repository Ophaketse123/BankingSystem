
package bank.controller;

import bank.dao.UserDAO;

public class LoginController {

    public boolean checkLogin(String username, String password)
            throws Exception {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Please enter your username.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Please enter your password.");
        }

        UserDAO dao = new UserDAO();

        return dao.checkLogin(username, password);
    }
}
