package entity;

import enums.AccountType;

import java.util.Objects;

public class CreditAccountEntity extends AccountEntity {

   /* private static final int CREDIT_PERIOD                = 12;
    private static final int TOTAL_CREDIT_PERIOD_IN_MONTH = 36;*/

    private final Double      limit;
    private final Double      rate;
    private final Double      charge;
    private final AccountType type;

    private Double       liability;

    private CreditAccountEntity(CreditAccountBuilder builder) {
        super(builder);
        this.limit     = builder.limit;
        this.rate      = builder.rate;
        this.liability = builder.liability;
        this.type      = builder.accountType;
        this.charge    = builder.creditCharge;

    }

  /*  private Double percents() {
        return rate / CREDIT_PERIOD;
    }

    private Double calculateCreditLiabilityPerMonth() {
        return (limit * percents()) / (1 - Math.pow(1 + percents(), -TOTAL_CREDIT_PERIOD_IN_MONTH));
    }*/

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditAccountEntity that = (CreditAccountEntity) o;
        return Objects.equals(limit, that.limit) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(liability, that.liability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, rate, charge, liability);
    }

    public static class CreditAccountBuilder extends AccountBuilder<CreditAccountBuilder> {
        private Double rate;
        private Double limit;
        private Double liability;
        private Double creditCharge;
        private AccountType accountType;

        public CreditAccountBuilder() {
        }

        public CreditAccountBuilder withCreditLimit(Double limit) {
            this.limit = limit;
            return self();
        }
        public CreditAccountBuilder withCreditRate(Double rate) {
            this.rate = rate;
            return self();
        }
        public CreditAccountBuilder withCreditLiability(Double liability){
            this.liability = liability;
            return self();
        }
        public CreditAccountBuilder withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return self();
        }

        public CreditAccountBuilder withCreditCharge(Double creditCharge) {
            this.creditCharge = creditCharge;
            return self();
        }

        @Override
        public CreditAccountEntity build() {
            return new CreditAccountEntity(this);
        }

        @Override
        protected CreditAccountBuilder self() {
            return this;
        }
    }

}
