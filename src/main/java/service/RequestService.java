package service;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Request;

public interface RequestService {

    void save(Request request);

    Pageable<Request> findAll(Page page);

    void deleteById(Integer id);

    Request findById(Integer id);

}
