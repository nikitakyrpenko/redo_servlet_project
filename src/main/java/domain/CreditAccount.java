package domain;

import domain.abstraction.InterestChargeable;
import enums.AccountType;
import enums.ChargeType;


import java.util.Objects;

public class CreditAccount extends Account implements InterestChargeable {

    private static final int CREDIT_PERIOD = 12;
    private static final int TOTAL_CREDIT_PERIOD_IN_MONTH = 36;

    private final Double limit;
    private final Double rate;
    private final Double charge;
    private final AccountType type;

    private Double liability;

    private CreditAccount(CreditAccountBuilder builder) {
        super(builder);
        this.limit = builder.limit;
        this.rate = builder.rate;
        this.liability = builder.liability;
        this.type = builder.accountType;
        this.charge = calculateCreditLiabilityPerMonth();

    }

    private Double percents() {
        return rate / CREDIT_PERIOD;
    }

    private Double calculateCreditLiabilityPerMonth() {
        return (limit * percents()) / (1 - Math.pow(1 + percents(), -TOTAL_CREDIT_PERIOD_IN_MONTH));
    }

    @Override
    public Charge processCharge() {

        Charge incomingCharge = new Charge(this.charge, ChargeType.CREDIT_ARRIVAL, getId());
        this.liability = this.liability + this.charge;
        return incomingCharge;
    }

    @Override
    public AccountType getAccountType() {
        return type;
    }

    @Override
    public Double getCharge() {
        return charge;
    }

    public Double getRate() {
        return rate;
    }

    public Double getLimit() {
        return limit;
    }

    public Double getLiability() {
        return liability;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "limit=" + limit +
                ", rate=" + rate +
                ", charge=" + charge +
                ", liability=" + liability +
                "} " + super.toString() + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, rate, charge, liability);
    }

    public static class CreditAccountBuilder extends Account.AccountBuilder<CreditAccountBuilder> {
        private Double rate;
        private Double limit;
        private Double liability;
        private AccountType accountType;

        public CreditAccountBuilder() {
        }

        public CreditAccountBuilder withCreditLimit(Double limit) {
            super.withBalance(limit);
            this.limit = limit;
            return self();
        }

        public CreditAccountBuilder withCreditRate(Double rate) {
            if (rate <= 0.0) {
                throw new IllegalArgumentException("credit rate should be more than zero");
            }
            this.rate = rate;
            return self();
        }

        public CreditAccountBuilder withCreditLiability(Double liability) {
            this.liability = liability;
            return self();
        }

        public CreditAccountBuilder withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return self();
        }

        @Override
        public CreditAccount build() {
            return new CreditAccount(this);
        }

        @Override
        protected CreditAccountBuilder self() {
            return this;
        }
    }

}
