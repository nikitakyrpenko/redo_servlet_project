package entity;

import enums.AccountType;

import java.util.Objects;

public class RequestEntity {

    private Integer id;
    private final AccountType accountType;
    private final Integer userId;

    public RequestEntity( AccountType accountType, Integer userId) {
        this.accountType = accountType;
        this.userId = userId;
    }

    public RequestEntity(Integer id, AccountType accountType, Integer userId) {
        this.id = id;
        this.accountType = accountType;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestEntity that = (RequestEntity) o;
        return Objects.equals(id, that.id) &&
                accountType == that.accountType &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, userId);
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
}
