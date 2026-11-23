
package bank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentAccount extends Account implements InterestBearing {

    private static final BigDecimal RATE = new BigDecimal("0.05");

    public InvestmentAccount(String accountNumber, String branch, BigDecimal openingAmount) {
        super(accountNumber, branch);

        if (openingAmount == null || openingAmount.compareTo(new BigDecimal("500")) < 0) {
            throw new IllegalArgumentException("Minimum opening deposit is P500.");
        }

        deposit(openingAmount, "Opening deposit");
    }

    // Withdraw money from the account
    @Override
    public Transaction withdraw(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid amount.");
        }

        if (amount.compareTo(getBalance()) > 0) {
            throw new IllegalArgumentException("Not enough money in the account.");
        }

        adjustBalance(amount.negate());

        Transaction t = new Transaction(TransactionType.WITHDRAWAL,
                amount, getBalance(), "Cash withdrawal");

        getTransactions().add(t);
        return t;
    }

    @Override
    public BigDecimal getInterestRate() {
        return RATE;
    }

    @Override
    public BigDecimal calculateInterest() {
        return getBalance().multiply(RATE).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Transaction payInterest() {

        BigDecimal interest = calculateInterest();

        if (interest.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        adjustBalance(interest);

        Transaction t = new Transaction(TransactionType.INTEREST,
                interest, getBalance(), "Monthly investment interest");

        getTransactions().add(t);
        return t;
    }
}


