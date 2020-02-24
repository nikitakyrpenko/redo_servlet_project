package mapper.impl;

import domain.Request;
import entity.RequestEntity;
import mapper.Mapper;

public class RequestMapper implements Mapper<Request, RequestEntity> {

    @Override
    public Request mapEntityToDomain(RequestEntity entity) {
        return new Request(entity.getId(), entity.getAccountType(), entity.getUserId());
    }

    @Override
    public RequestEntity mapDomainToEntity(Request domain) {
        return new RequestEntity(domain.getAccountType(), domain.getUserId());
    }
}
