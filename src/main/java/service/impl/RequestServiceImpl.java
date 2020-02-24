package service.impl;

import dao.RequestDao;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Request;
import entity.RequestEntity;
import mapper.Mapper;
import service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

public class RequestServiceImpl implements RequestService {

    private final RequestDao requestDao;
    private final Mapper<Request, RequestEntity> mapper;

    public RequestServiceImpl(RequestDao requestDao, Mapper<Request, RequestEntity> mapper) {
        this.requestDao = requestDao;
        this.mapper = mapper;
    }

    @Override
    public void save(Request request) {
        requestDao.save(mapper.mapDomainToEntity(request));
    }

    @Override
    public Pageable<Request> findAll(Page page) {
        Pageable<RequestEntity> requests = requestDao.findAll(page);

        List<Request> collect = requests.getItems()
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());

        return new Pageable<>(collect, requests.getPageNumber(),requests.getItemsNumberPerPage(),requests.getCountAll());
    }

    @Override
    public void deleteById(Integer id) {
        requestDao.deleteById(id);
    }

    @Override
    public Request findById(Integer id) {
        return requestDao.findById(id)
                .map(mapper::mapEntityToDomain)
                .orElseThrow();
    }
}
