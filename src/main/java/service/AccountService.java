package service;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Account;

import java.sql.Date;
import java.util.List;

public interface AccountService {

    Pageable<Account> findAllByOwnerId(Integer id, Page page);

    Account findById(Integer id);

    void save(Account account);

    List<Account> findAll();


}
