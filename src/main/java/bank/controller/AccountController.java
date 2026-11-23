
package bank.controller;

import bank.dao.AccountDAO;
import bank.dao.TransactionDAO;
import bank.model.Account;
import bank.model.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountController {

    // Deposit into an account
    public Transaction deposit(Account account, String amount) {

        if (account == null) {
            throw new IllegalArgumentException("Please select an account.");
        }

        BigDecimal money = checkAmount(amount);
        return account.deposit(money);
    }

    // Withdraw from an account
    public Transaction withdraw(Account account, String amount) {

        if (account == null) {
            throw new IllegalArgumentException("Please select an account.");
        }

        BigDecimal money = checkAmount(amount);
        return account.withdraw(money);
    }

    // View account balance
    public BigDecimal viewBalance(Account account) {

        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        return account.getBalance();
    }

    // Check the amount entered
    private BigDecimal checkAmount(String amount) {

        if (amount == null || amount.isBlank()) {
            throw new IllegalArgumentException("Please enter an amount.");
        }

        try {
            BigDecimal money = new BigDecimal(amount.trim());

            if (money.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Amount must be greater than zero.");
            }

            return money;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a valid number.");
        }
    }

    // Check whether the customer owns the account
    public void checkAccountOwner(String customerId, String accountNumber)
            throws SQLException {

        if (customerId == null || customerId.isBlank() ||
                accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Enter a valid account number.");
        }

        AccountDAO dao = new AccountDAO();

        boolean owner = dao.customerOwnsAccount(
                customerId, accountNumber.trim());

        if (!owner) {
            throw new IllegalArgumentException(
                    "This account does not belong to you.");
        }
    }

    // Deposit money into H2
    public BigDecimal depositSaved(String customerId,
                                   String accountNumber, String amount)
            throws SQLException {

        checkAccountOwner(customerId, accountNumber);

        BigDecimal money = checkAmount(amount);

        AccountDAO dao = new AccountDAO();

        return dao.depositToDatabase(accountNumber.trim(), money);
    }

    // Withdraw money from H2
    public BigDecimal withdrawSaved(String customerId,
                                    String accountNumber, String amount)
            throws SQLException {

        checkAccountOwner(customerId, accountNumber);

        BigDecimal money = checkAmount(amount);

        AccountDAO dao = new AccountDAO();

        return dao.withdrawFromDatabase(accountNumber.trim(), money);
    }

    // Get transaction history
    public String viewTransactionHistory(String accountNumber)
            throws SQLException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Enter account number.");
        }

        TransactionDAO dao = new TransactionDAO();

        return dao.getTransactionHistory(accountNumber.trim());
    }
}
