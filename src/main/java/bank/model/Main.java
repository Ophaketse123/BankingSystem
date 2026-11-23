
package bank.model;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        // Create a customer
        IndividualCustomer customer = new IndividualCustomer(
                "C001", "Gaborone", "70000000",
                "test@example.com", "Neo", "Moyo",
                "123456789", true);

        // Create savings account
        SavingsAccount savings = new SavingsAccount("S001", "Main Branch");
        customer.addAccount(savings);

        savings.deposit(new BigDecimal("1000"));
        savings.payInterest();

        System.out.println("Customer: " + customer.getFirstName());
        System.out.println("Savings Balance: P" + savings.getBalance());

        // Create investment account
        InvestmentAccount investment = new InvestmentAccount(
                "I001", "Main Branch", new BigDecimal("500"));

        customer.addAccount(investment);

        investment.withdraw(new BigDecimal("100"));
        investment.payInterest();

        System.out.println("Investment Balance: P" + investment.getBalance());

        // Create cheque account
        Employer employer = new Employer("ABC Company", "Gaborone");

        ChequeAccount cheque = new ChequeAccount(
                "CH001", "Main Branch", employer);

        customer.addAccount(cheque);

        cheque.deposit(new BigDecimal("800"));
        cheque.withdraw(new BigDecimal("200"));

        System.out.println("Cheque Balance: P" + cheque.getBalance());

    }
}


