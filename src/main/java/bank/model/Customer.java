
package bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {

    private String customerId;
    private String address;
    private String phone;
    private String email;
    private List<Account> accounts;

    public Customer(String customerId, String address, String phone, String email) {
        this.customerId = customerId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    // Add an account to a customer
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be empty.");
        }

        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


