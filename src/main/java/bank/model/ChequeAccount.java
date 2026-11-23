
package bank.model;

import java.math.BigDecimal;

public class ChequeAccount extends Account {

    private Employer employer;

    public ChequeAccount(String accountNumber, String branch, Employer employer) {
        super(accountNumber, branch);

        if (employer == null) {
            throw new IllegalArgumentException("Employer details are required.");
        }

        if (employer.getEmployerName() == null ||
                employer.getEmployerName().isBlank() ||
                employer.getEmployerAddress() == null ||
                employer.getEmployerAddress().isBlank()) {
            throw new IllegalArgumentException("Enter valid employer details.");
        }

        this.employer = employer;
    }

    // Withdraw money if the balance is enough
    @Override
    public Transaction withdraw(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid amount.");
        }

        if (amount.compareTo(getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        adjustBalance(amount.negate());

        Transaction t = new Transaction(TransactionType.WITHDRAWAL,
                amount, getBalance(), "Cheque account withdrawal");

        getTransactions().add(t);

        return t;
    }

    public Employer getEmployer() {
        return employer;
    }
}


