
package bank.app;

import bank.controller.AccountController;
import bank.controller.RegistrationController;
import bank.controller.OpenAccountController;
import bank.controller.InterestController;
import bank.controller.LoginController;

import bank.dao.AccountDAO;
import bank.dao.UserDAO;

import bank.view.AccountView;
import bank.view.RegistrationView;
import bank.view.OpenAccountView;
import bank.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BankingApplication extends Application {

    @Override
    public void start(Stage stage) {

        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController();

        // LOGIN BUTTON
        loginView.getLoginButton().setOnAction(e -> {

            try {
                String username = loginView.getUsername();
                String password = loginView.getPassword();

                boolean correct = loginController.checkLogin(
                        username, password);

                if (correct) {

                    UserDAO userDAO = new UserDAO();

                    String customerId =
                            userDAO.getCustomerId(username);

                    if (customerId == null || customerId.isBlank()) {
                        showError(
                                "Login Error",
                                "No customer linked to this login."
                        );
                        return;
                    }

                    showBankingScreen(stage, customerId);

                } else {
                    showError(
                            "Login Error",
                            "Invalid username or password."
                    );
                }

            } catch (Exception ex) {
                showError(
                        "Login Error",
                        "Unable to verify login details."
                );
            }
        });

        // LOGIN WINDOW
        Scene scene = new Scene(loginView, 500, 460);

        stage.setTitle("PulaTrust Bank - Login");
        stage.setScene(scene);
        stage.show();
    }

    // MAIN BANKING DASHBOARD
    private void showBankingScreen(Stage stage, String customerId) {

        AccountView view = new AccountView();

        AccountController controller = new AccountController();
        AccountDAO accountDAO = new AccountDAO();

        // DEPOSIT BUTTON
        view.getDepositButton().setOnAction(e -> {

            try {
                String accountNumber = view.getAccountNumber();
                String amount = view.getAmount();

                BigDecimal balance = controller.depositSaved(
                        customerId,
                        accountNumber,
                        amount
                );

                view.showBalance(balance.toString());
                view.showMessage("Deposit successful.");

            } catch (IllegalArgumentException | SQLException ex) {
                showError("Deposit Error", ex.getMessage());
            }
        });

        // WITHDRAW BUTTON
        view.getWithdrawButton().setOnAction(e -> {

            try {
                String accountNumber = view.getAccountNumber();
                String amount = view.getAmount();

                BigDecimal balance = controller.withdrawSaved(
                        customerId,
                        accountNumber,
                        amount
                );

                view.showBalance(balance.toString());
                view.showMessage("Withdrawal successful.");

            } catch (IllegalArgumentException | SQLException ex) {
                showError("Withdrawal Error", ex.getMessage());
            }
        });

        // VIEW BALANCE BUTTON
        view.getBalanceButton().setOnAction(e -> {

            try {
                String accountNumber =
                        view.getAccountNumber().trim();

                if (accountNumber.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Please enter an account number."
                    );
                }

                // Verify account ownership
                controller.checkAccountOwner(
                        customerId,
                        accountNumber
                );

                BigDecimal balance =
                        accountDAO.getSavedBalance(accountNumber);

                view.showBalance(balance.toString());
                view.showMessage("Balance loaded successfully.");

            } catch (IllegalArgumentException | SQLException ex) {
                showError("Account Access Error", ex.getMessage());
            }
        });

        // TRANSACTION HISTORY BUTTON
        view.getHistoryButton().setOnAction(e -> {

            try {
                String accountNumber = view.getAccountNumber();

                controller.checkAccountOwner(
                        customerId,
                        accountNumber
                );

                String history =
                        controller.viewTransactionHistory(accountNumber);

                TextArea textArea = new TextArea(history);

                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefSize(450, 300);

                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setTitle(
                        "PulaTrust Bank - Transaction History"
                );

                alert.setHeaderText(
                        "Account: " + accountNumber
                );

                alert.getDialogPane().setContent(textArea);
                alert.showAndWait();

            } catch (IllegalArgumentException | SQLException ex) {
                showError(
                        "Transaction History Error",
                        ex.getMessage()
                );
            }
        });

        // REGISTER CUSTOMER BUTTON
        view.getRegisterButton().setOnAction(e -> {

            RegistrationView registrationView =
                    new RegistrationView();

            RegistrationController registrationController =
                    new RegistrationController();

            Stage registrationStage = new Stage();

            registrationStage.setTitle(
                    "PulaTrust Bank - Customer Registration"
            );

            registrationStage.setScene(
                    new Scene(registrationView, 460, 650)
            );

            // REGISTER NEW CUSTOMER
            registrationView.getRegisterButton().setOnAction(event -> {

                try {

                    // Get username and password
                    String username =
                            registrationView.getUsername().trim();

                    String password =
                            registrationView.getPassword();

                    String confirmPassword =
                            registrationView.getConfirmPassword();

                    // Validate username
                    if (username.isEmpty()) {
                        throw new IllegalArgumentException(
                                "Please enter a username."
                        );
                    }

                    // Validate password
                    if (password.isBlank()) {
                        throw new IllegalArgumentException(
                                "Please enter a password."
                        );
                    }

                    if (password.length() < 8) {
                        throw new IllegalArgumentException(
                                "Password must have at least 8 characters."
                        );
                    }

                    // Confirm passwords match
                    if (!password.equals(confirmPassword)) {
                        throw new IllegalArgumentException(
                                "Passwords do not match."
                        );
                    }

                    UserDAO userDAO = new UserDAO();

                    // Check if username already exists
                    String existingCustomerId =
                            userDAO.getCustomerId(username);

                    if (existingCustomerId != null) {
                        throw new IllegalArgumentException(
                                "Username already exists. Choose another."
                        );
                    }

                    // Save customer information
                    String newCustomerId =
                            registrationController.registerIndividual(
                                    registrationView.getFirstName(),
                                    registrationView.getSurname(),
                                    registrationView.getIdNumber(),
                                    registrationView.getAddress(),
                                    registrationView.getPhone(),
                                    registrationView.getEmail(),
                                    registrationView.isEmployed()
                            );

                    // Save username and hashed password
                    userDAO.saveUser(
                            username,
                            password,
                            newCustomerId
                    );

                    // Display successful registration
                    showInformation(
                            "Registration Successful",
                            "Welcome to PulaTrust Bank!\n\n" +
                                    "Customer registered successfully.\n" +
                                    "Customer ID: " + newCustomerId + "\n" +
                                    "Username: " + username + "\n\n" +
                                    "You can now log in using your new credentials."
                    );

                    registrationStage.close();

                } catch (Exception ex) {

                    // Handles validation, SQL, and UserDAO exceptions
                    showError(
                            "Registration Error",
                            ex.getMessage() == null
                                    ? "Unable to register customer."
                                    : ex.getMessage()
                    );
                }
            });

            registrationStage.show();
        });

        // OPEN BANK ACCOUNT BUTTON
        view.getOpenAccountButton().setOnAction(e -> {

            OpenAccountView openView = new OpenAccountView();

            OpenAccountController openController =
                    new OpenAccountController();

            Stage openStage = new Stage();

            openStage.setTitle(
                    "PulaTrust Bank - Open Account"
            );

            openStage.setScene(
                    new Scene(openView, 460, 640)
            );

            openView.getOpenButton().setOnAction(event -> {

                try {
                    String accountNumber =
                            openController.openAccount(
                                    customerId,
                                    openView.getCustomerId(),
                                    openView.getBranch(),
                                    openView.getAccountType(),
                                    openView.getOpeningAmount(),
                                    openView.getEmployerName(),
                                    openView.getEmployerAddress()
                            );

                    showInformation(
                            "Account Opening Successful",
                            "Account opened successfully!\n" +
                                    "Account Number: " + accountNumber
                    );

                    openStage.close();

                } catch (IllegalArgumentException | SQLException ex) {
                    showError(
                            "Account Opening Error",
                            ex.getMessage()
                    );
                }
            });

            openStage.show();
        });

        // PAY MONTHLY INTEREST BUTTON
        view.getInterestButton().setOnAction(e -> {

            try {
                String accountNumber = view.getAccountNumber();

                controller.checkAccountOwner(
                        customerId,
                        accountNumber
                );

                InterestController interestController =
                        new InterestController();

                BigDecimal interest =
                        interestController.payInterest(accountNumber);

                BigDecimal balance =
                        accountDAO.getSavedBalance(
                                accountNumber.trim()
                        );

                view.showBalance(balance.toString());

                view.showMessage(
                        "Interest paid: P" + interest
                );

            } catch (IllegalArgumentException | SQLException ex) {
                showError(
                        "Interest Error",
                        ex.getMessage()
                );
            }
        });

        // DISPLAY DASHBOARD
        Scene scene = new Scene(view, 550, 720);

        stage.setTitle(
                "PulaTrust Bank - Dashboard - " + customerId
        );

        stage.setScene(scene);
        stage.show();
    }

    // ERROR MESSAGE POPUP
    private void showError(String title, String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(
                message == null ? "An error occurred." : message
        );
        alert.showAndWait();
    }

    // SUCCESS MESSAGE POPUP
    private void showInformation(String title, String message) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // MAIN METHOD
    public static void main(String[] args) {
        launch(args);
    }
}
