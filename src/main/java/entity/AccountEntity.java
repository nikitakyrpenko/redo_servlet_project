package entity;

import enums.AccountType;

import java.util.Date;

public abstract class AccountEntity {

    private final Integer id;
    private final Date expirationDate;
    private Integer holder;
    private Double balance;

    public AccountEntity(AccountBuilder builder) {
        this.id = builder.id;
        this.holder = builder.holder;
        this.balance = builder.balance;
        this.expirationDate = builder.expirationDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHolder() {
        return holder;
    }

    public Double getBalance() {
        return balance;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public abstract Double getCharge();

    public abstract AccountType getAccountType();

    public void setHolder(Integer holder) {
        this.holder = holder;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", holder=" + holder +
                ", expirationDate=" + expirationDate +
                ", balance=" + balance +
                '}';
    }

    public abstract static class AccountBuilder<SELF extends AccountBuilder<SELF>> {
        private Integer id;
        private Integer holder;
        private Double balance;
        private Date expirationDate;

        public AccountBuilder() {
        }

        public SELF withId(Integer id) {
            this.id = id;
            return self();
        }

        public SELF withHolder(Integer holder) {
            this.holder = holder;
            return self();
        }

        public SELF withDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return self();
        }

        public SELF withBalance(Double balance) {
            this.balance = balance;
            return self();
        }

        public abstract AccountEntity build();

        @SuppressWarnings("unchecked")
        protected SELF self() {
            return (SELF) this;
        }
    }
}
