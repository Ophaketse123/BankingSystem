
package bank.controller;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

public class OpenAccountController {

    public String openAccount(String loggedInCustomerId,
                              String customerId,
                              String branch,
                              String accountType,
                              String openingAmount,
                              String employerName,
                              String employerAddress)
            throws SQLException {

        // Check that the customer is opening their own account
        if (customerId == null || loggedInCustomerId == null ||
                !loggedInCustomerId.equals(customerId.trim())) {

            throw new IllegalArgumentException(
                    "You can only open accounts for yourself.");
        }

        if (customerId.isBlank() ||
                branch == null || branch.isBlank()) {

            throw new IllegalArgumentException(
                    "Enter customer ID and branch.");
        }

        // Find the customer in the database
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.findCustomer(customerId.trim());

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }

        // Check opening amount
        BigDecimal amount;

        try {
            amount = new BigDecimal(openingAmount.trim());

        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Enter a valid opening amount.");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0 ||
                amount.scale() > 2) {

            throw new IllegalArgumentException(
                    "Enter a valid amount.");
        }

        // Generate account number
        String accountNumber = "A" +
                UUID.randomUUID().toString()
                        .replace("-", "")
                        .substring(0, 12);

        Account account;

        // Savings account
        if ("Savings".equals(accountType)) {

            account = new SavingsAccount(accountNumber, branch);

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                account.deposit(amount, "Opening deposit");
            }

        }

        // Investment account
        else if ("Investment".equals(accountType)) {

            account = new InvestmentAccount(
                    accountNumber, branch, amount);
        }

        // Cheque account
        else if ("Cheque".equals(accountType)) {

            if (!(customer instanceof IndividualCustomer)) {
                throw new IllegalArgumentException(
                        "Cheque accounts are for employed individuals.");
            }

            IndividualCustomer person = (IndividualCustomer) customer;

            if (!person.isEmployed()) {
                throw new IllegalArgumentException(
                        "Customer must be employed.");
            }

            if (employerName == null || employerName.isBlank() ||
                    employerAddress == null || employerAddress.isBlank()) {

                throw new IllegalArgumentException(
                        "Enter employer details.");
            }

            Employer employer = new Employer(
                    employerName, employerAddress);

            account = new ChequeAccount(
                    accountNumber, branch, employer);

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                account.deposit(amount, "Opening deposit");
            }

        } else {
            throw new IllegalArgumentException(
                    "Select an account type.");
        }

        // Add account to the customer
        customer.addAccount(account);

        // Save account to H2
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.saveAccount(customerId.trim(), account);

        // Save opening deposit transaction
        TransactionDAO transactionDAO = new TransactionDAO();

        for (Transaction transaction : account.getTransactions()) {
            transactionDAO.saveTransaction(accountNumber, transaction);
        }

        return accountNumber;
    }
}
