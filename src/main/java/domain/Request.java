package domain;

import enums.AccountType;

import java.util.Objects;

public class Request {

    private Integer id;
    private final AccountType accountType;
    private final Integer userId;

    public Request(AccountType accountType, Integer userId) {
        this.accountType = accountType;
        this.userId = userId;
    }

    public Request(Integer id, AccountType accountType, Integer userId) {
        this.id = id;
        this.accountType = accountType;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                accountType == request.accountType &&
                Objects.equals(userId, request.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, userId);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", userId=" + userId +
                '}';
    }
}
