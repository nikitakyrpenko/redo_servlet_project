package entity;

import enums.ChargeType;

import java.util.Objects;

public class ChargeEntity {

    private  Integer    id;
    private final Double     charge;
    private final ChargeType chargeType;
    private final Integer accountEntity;

    public ChargeEntity(Double charge, ChargeType chargeType, Integer accountEntity){
        this.charge = charge;
        this.chargeType = chargeType;
        this.accountEntity = accountEntity;
    }

    public ChargeEntity(Integer id, Double charge, ChargeType chargeType, Integer accountEntity) {
        this.id = id;
        this.charge = charge;
        this.chargeType = chargeType;
        this.accountEntity = accountEntity;
    }

    public Integer getId() {
        return id;
    }

    public Double getCharge() {
        return charge;
    }

    public Integer getAccountEntity() {
        return accountEntity;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", charge=" + charge +
                ", chargeType=" + chargeType +
                ", account=" + accountEntity +
                '}'+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeEntity chargeEntity1 = (ChargeEntity) o;
        return Objects.equals(id, chargeEntity1.id) &&
                Objects.equals(charge, chargeEntity1.charge) &&
                chargeType == chargeEntity1.chargeType &&
                Objects.equals(accountEntity, chargeEntity1.accountEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, charge, chargeType, accountEntity);
    }
}

