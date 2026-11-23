
package bank.model;

public class IndividualCustomer extends Customer {

    private String firstName;
    private String surname;
    private String idNumber;
    private boolean employed;

    public IndividualCustomer(String customerId, String address,
                              String phone, String email,
                              String firstName, String surname,
                              String idNumber, boolean employed) {

        super(customerId, address, phone, email);

        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.employed = employed;
    }

    // Only employed individuals can open cheque accounts
    @Override
    public void addAccount(Account account) {
        if (account instanceof ChequeAccount && !employed) {
            throw new IllegalArgumentException(
                    "Only employed customers can open cheque accounts.");
        }

        super.addAccount(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
}


