package mapper.impl;

import domain.Operation;
import entity.OperationEntity;
import mapper.Mapper;

public class OperationMapper implements Mapper<Operation, OperationEntity> {

    @Override
    public Operation mapEntityToDomain(OperationEntity entity) {
       return Operation.builder()
               .withId(entity.getId())
               .withDate(entity.getDateOfTransaction())
               .withPurpose(entity.getPurposeOfTransaction())
               .withTransfer(entity.getTransfer())
               .withReceiver(entity.getReceiverOfTransaction())
               .withSender(entity.getSenderOfTransaction())
               .build();
    }

    @Override
    public OperationEntity mapDomainToEntity(Operation domain) {
        return OperationEntity.builder()
                .withId(domain.getId())
                .withDate(domain.getDateOfTransaction())
                .withPurpose(domain.getPurposeOfTransaction())
                .withTransfer(domain.getTransfer())
                .withSender(domain.getSenderOfTransaction())
                .withReceiver(domain.getReceiverOfTransaction())
                .build();
    }
}
