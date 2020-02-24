package dao;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.ChargeEntity;

public interface ChargeDao extends CrudPageableDao<ChargeEntity> {

    Pageable<ChargeEntity> findAllByAccountId(Integer id, Page page);

}
