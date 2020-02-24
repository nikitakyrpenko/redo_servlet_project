package dao;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.AccountEntity;
import entity.OperationEntity;

public interface OperationDao extends CrudPageableDao<OperationEntity> {

    Pageable<OperationEntity> findAllByAccountId(Integer id, Page page);

    void save(OperationEntity operationEntity, AccountEntity sender, AccountEntity receiver);
}
