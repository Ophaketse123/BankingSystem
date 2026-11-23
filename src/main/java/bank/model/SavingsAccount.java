package bank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class SavingsAccount extends Account implements InterestBearing {

    private static final BigDecimal RATE = new BigDecimal("0.0005");

    public SavingsAccount(String accountNumber, String branch) {
        super(accountNumber,branch);
    }

    //Savings accounts do not allow withdrawals
    @Override
    public Transaction withdraw(BigDecimal amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from a savings account");
    }
    @Override
    public BigDecimal getInterestRate() {
        return RATE;
    }
    @Override
    public BigDecimal calculateInterest() {
        return getBalance().multiply(RATE).setScale(2,RoundingMode.HALF_UP);
    }
    @Override
    public Transaction payInterest() {
        BigDecimal interest = calculateInterest();
        if (interest.compareTo(BigDecimal.ZERO) <= 0) {
            return null;   // nothing to pay
        }
        adjustBalance(interest);
        Transaction t = new Transaction(TransactionType.INTEREST, interest,getBalance(),"Monthly interest");
        getTransactions().add(t);
        return t;

    }

}
