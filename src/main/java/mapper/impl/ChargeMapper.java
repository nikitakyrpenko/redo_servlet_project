package mapper.impl;

import domain.Charge;
import entity.ChargeEntity;
import mapper.Mapper;

public class ChargeMapper implements Mapper<Charge, ChargeEntity> {

    @Override
    public Charge mapEntityToDomain(ChargeEntity entity) {
        return new Charge(entity.getId(), entity.getCharge(), entity.getChargeType(), entity.getAccountEntity());
    }

    @Override
    public ChargeEntity mapDomainToEntity(Charge domain) {
        return new ChargeEntity(domain.getCharge(), domain.getChargeType(), domain.getAccountEntity());
    }
}
