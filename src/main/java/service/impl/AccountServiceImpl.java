package service.impl;

import dao.AccountDao;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Account;
import entity.AccountEntity;
import mapper.Mapper;
import service.AccountService;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final Mapper<Account, AccountEntity> mapper;

    public AccountServiceImpl(AccountDao accountDao, Mapper<Account, AccountEntity> mapper) {
        this.accountDao = accountDao;
        this.mapper = mapper;
    }


    @Override
    public Pageable<Account> findAllByOwnerId(Integer id, Page page) {
        Pageable<AccountEntity> allByOwnerId = accountDao.findAllByOwnerId(id, page);

        List<Account> accounts = allByOwnerId.getItems()
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());

        return new Pageable<>(accounts, allByOwnerId.getPageNumber(), allByOwnerId.getItemsNumberPerPage(), allByOwnerId.getCountAll());
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id)
                .map(mapper::mapEntityToDomain)
                .orElseThrow();
    }

    @Override
    public void save(Account account) {
        accountDao.save(mapper.mapDomainToEntity(account));
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll()
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
