package bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private BigDecimal balance;
    private String branch;
    private LocalDate openDate;
    private List<Transaction> transactions;

    public Account(String accountNumber, String branch) {
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.balance = BigDecimal.ZERO;
        this.openDate = LocalDate.now();
        this.transactions = new ArrayList<>();
    }


    // deposit without a note
    public Transaction deposit(BigDecimal amount) {
        return deposit(amount, "Cash deposit");
    }

    // deposit with a note
    public Transaction deposit(BigDecimal amount, String note) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid deposit amount.");
        }

        adjustBalance(amount);

        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, balance, note);
        transactions.add(t);

        return t;
    }


    // every account type decides its own withdrawal rules
    public abstract Transaction withdraw(BigDecimal amount);

    protected void adjustBalance(BigDecimal delta) {
        balance = balance.add(delta);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}