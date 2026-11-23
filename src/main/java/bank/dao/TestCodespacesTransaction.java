
package bank.dao;

import java.math.BigDecimal;

public class TestCodespacesTransaction {

    public static void main(String[] args) throws Exception {

        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        String accountNumber = "CH202";

        // Display balance before transactions
        BigDecimal before =
                accountDAO.getSavedBalance(accountNumber);

        System.out.println("PulaTrust Bank Transaction Test");
        System.out.println("Starting balance: P" + before);

        // Deposit P100
        BigDecimal afterDeposit =
                accountDAO.depositToDatabase(
                        accountNumber,
                        new BigDecimal("100.00")
                );

        System.out.println("After deposit: P" + afterDeposit);

        // Withdraw P50
        BigDecimal afterWithdrawal =
                accountDAO.withdrawFromDatabase(
                        accountNumber,
                        new BigDecimal("50.00")
                );

        System.out.println("After withdrawal: P" + afterWithdrawal);

        // Read transaction history
        String history =
                transactionDAO.getTransactionHistory(accountNumber);

        System.out.println("Transaction History:");
        System.out.println(history);
    }
}
