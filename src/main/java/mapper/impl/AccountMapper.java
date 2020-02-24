package mapper.impl;

import domain.Account;
import domain.CreditAccount;
import domain.DepositAccount;
import entity.AccountEntity;
import entity.CreditAccountEntity;
import entity.DepositAccountEntity;
import enums.AccountType;
import mapper.Mapper;

public class AccountMapper implements Mapper<Account, AccountEntity> {
    @Override
    public Account mapEntityToDomain(AccountEntity entity) {
        Account account;
        if (entity.getAccountType() == AccountType.CREDIT) {
            CreditAccountEntity creditAccountEntity = (CreditAccountEntity) entity;
            account = new CreditAccount.CreditAccountBuilder()
                    .withId(creditAccountEntity.getId())
                    .withHolder(entity.getHolder())
                    .withDate(creditAccountEntity.getExpirationDate())
                    .withBalance(creditAccountEntity.getBalance())
                    .withCreditLimit(creditAccountEntity.getLimit())
                    .withCreditRate(creditAccountEntity.getRate())
                    .withCreditLiability(creditAccountEntity.getLiability())
                    .withAccountType(AccountType.CREDIT)
                    .build();
        } else {
            DepositAccountEntity depositAccountEntity = (DepositAccountEntity) entity;
            account = new DepositAccount.DepositAccountBuilder()
                    .withId(depositAccountEntity.getId())
                    .withDate(depositAccountEntity.getExpirationDate())
                    .withBalance(depositAccountEntity.getBalance())
                    .withHolder(entity.getHolder())
                    .withDepositAmount(depositAccountEntity.getDepositAmount())
                    .withDepositRate(depositAccountEntity.getDepositRate())
                    .withAccountType(AccountType.DEPOSIT)
                    .build();
        }
        return account;
    }

    @Override
    public AccountEntity mapDomainToEntity(Account domain) {
        AccountEntity accountEntity;

        if (domain.getAccountType().equals(AccountType.DEPOSIT)) {
            DepositAccount depositAccount = (DepositAccount) domain;
            accountEntity = new DepositAccountEntity
                    .DepositAccountBuilder()
                    .withHolder(depositAccount.getHolder())
                    .withBalance(depositAccount.getBalance())
                    .withAccountType(depositAccount.getAccountType())
                    .withDate(depositAccount.getExpirationDate())
                    .withDepositAmount(depositAccount.getDepositAmount())
                    .withDepositRate(depositAccount.getDepositRate())
                    .build();
        } else {
            CreditAccount creditAccount = (CreditAccount) domain;
            accountEntity = new CreditAccountEntity.CreditAccountBuilder()
                    .withHolder(creditAccount.getHolder())
                    .withBalance(creditAccount.getBalance())
                    .withDate(creditAccount.getExpirationDate())
                    .withAccountType(creditAccount.getAccountType())
                    .withCreditCharge(creditAccount.getCharge())
                    .withCreditLiability(creditAccount.getLiability())
                    .withCreditLimit(creditAccount.getLimit())
                    .withCreditRate(creditAccount.getRate())
                    .build();
        }
        return accountEntity;
    }
}
