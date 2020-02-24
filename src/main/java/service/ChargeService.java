package service;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Charge;

public interface ChargeService {

    void applyChargesByEndOfMonth();

    Pageable<Charge> findAllByAccountId(Integer id, Page page);

    void save(Charge charge);

}
