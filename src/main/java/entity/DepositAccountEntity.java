package entity;

import enums.AccountType;

import java.util.Objects;

public class DepositAccountEntity extends AccountEntity {

    private final Double          depositRate;
    private final Double          depositAmount;
    private final AccountType accountType;

    private DepositAccountEntity(DepositAccountBuilder builder) {
        super(builder);
        this.depositRate   = builder.depositRate;
        this.depositAmount = builder.depositAmount;
        this.accountType   = builder.accountType;
    }

    public Double getDepositRate() {
        return depositRate;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }


    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public Double getCharge(){
        return super.getBalance() * depositRate;
    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "depositRate=" + depositRate +
                ", depositAmount=" + depositAmount +
                ", accountType=" + accountType +
                "} " + super.toString() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositAccountEntity that = (DepositAccountEntity) o;
        return Objects.equals(depositRate, that.depositRate) &&
                Objects.equals(depositAmount, that.depositAmount) &&
                accountType == that.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depositRate, depositAmount, accountType);
    }

    public static class DepositAccountBuilder extends AccountBuilder<DepositAccountBuilder> {

        private Double depositRate;
        private Double depositAmount;
        private AccountType accountType;

        public DepositAccountBuilder withDepositRate(double depositRate){
          /*  if (depositRate <= 0.0){throw new IllegalArgumentException("deposit rate should be more than zero");}*/
            this.depositRate = depositRate;
            return this;
        }
        public DepositAccountBuilder withDepositAmount(double depositAmount){
            super.withBalance(depositAmount);
            this.depositAmount = depositAmount;
            return this;
        }

        public DepositAccountBuilder withAccountType(AccountType accountType){
            this.accountType = accountType;
            return this;
        }

        @Override
        public DepositAccountEntity build() {
            return new DepositAccountEntity(this);
        }
    }

}
