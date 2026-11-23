package bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {
    private long transactionId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal balanceAfter;
    private String description;

    public Transaction(TransactionType type,BigDecimal amount,BigDecimal balanceAfter,
                        String description ) {
        this.type= type;
        this.amount=amount;
        this.balanceAfter=balanceAfter;
        this.description=description;
        this.dateTime=LocalDateTime.now();
    }

    public long getTransactionId() {
        return transactionId;
    }
    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }
}
