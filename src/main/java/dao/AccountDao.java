package dao;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.AccountEntity;

public interface AccountDao extends CrudPageableDao<AccountEntity> {

    Pageable<AccountEntity> findAllByOwnerId(Integer id, Page page);

}
