package bank.model;

import java.math.BigDecimal;

public interface InterestBearing {

    BigDecimal calculateInterest();
    Transaction payInterest();
    BigDecimal getInterestRate();

}
