package service.impl;

import dao.ChargeDao;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Charge;
import entity.ChargeEntity;
import mapper.Mapper;
import service.ChargeService;

import java.util.List;
import java.util.stream.Collectors;

public class ChargeServiceImpl implements ChargeService {

    private final ChargeDao chargeDao;
    private final Mapper<Charge, ChargeEntity> mapper;

    public ChargeServiceImpl(ChargeDao chargeDao, Mapper<Charge, ChargeEntity> mapper) {
        this.chargeDao = chargeDao;
        this.mapper = mapper;
    }

    @Override
    public void applyChargesByEndOfMonth() {

    }

    @Override
    public Pageable<Charge> findAllByAccountId(Integer id, Page page) {
        Pageable<ChargeEntity> allByAccountId = chargeDao.findAllByAccountId(id, page);

        List<Charge> charges = allByAccountId.getItems()
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());

        return new Pageable<>(charges, allByAccountId.getPageNumber(),allByAccountId.getItemsNumberPerPage(),allByAccountId.getCountAll());
    }

    @Override
    public void save(Charge charge) {
        chargeDao.save(mapper.mapDomainToEntity(charge));
    }
}
