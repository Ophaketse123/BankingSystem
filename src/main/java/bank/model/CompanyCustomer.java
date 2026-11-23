
package bank.model;

public class CompanyCustomer extends Customer {

    private String companyName;
    private String registrationNumber;

    public CompanyCustomer(String customerId, String address,
                           String phone, String email,
                           String companyName, String registrationNumber) {

        super(customerId, address, phone, email);

        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }

    // Companies cannot open cheque accounts
    @Override
    public void addAccount(Account account) {

        if (account instanceof ChequeAccount) {
            throw new IllegalArgumentException(
                    "Cheque accounts are only for employed individuals.");
        }

        super.addAccount(account);
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}


