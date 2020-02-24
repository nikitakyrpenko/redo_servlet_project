package domain;

import enums.ChargeType;

import java.util.Objects;

public class Charge {

    private Integer id;
    private final Double charge;
    private final ChargeType chargeType;
    private final Integer accountEntity;

    public Charge(Double charge, ChargeType chargeType, Integer accountEntity) {
        this.charge = charge;
        this.chargeType = chargeType;
        this.accountEntity = accountEntity;
    }

    public Charge(Integer id, Double charge, ChargeType chargeType, Integer accountEntity) {
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

    public ChargeType getChargeType() {
        return chargeType;
    }

    public Integer getAccountEntity() {
        return accountEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge1 = (Charge) o;
        return Objects.equals(id, charge1.id) &&
                Objects.equals(charge, charge1.charge) &&
                chargeType == charge1.chargeType &&
                Objects.equals(accountEntity, charge1.accountEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, charge, chargeType, accountEntity);
    }

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", charge=" + charge +
                ", chargeType=" + chargeType +
                ", accountEntity=" + accountEntity +
                '}';
    }
}
