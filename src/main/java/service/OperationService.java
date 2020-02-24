package service;

import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Operation;

public interface OperationService {

    void save(Operation operation);

    Pageable<Operation> findAllOperationsByAccountId(Integer id, Page page);

}
