package service.impl;

import dao.AccountDao;
import dao.OperationDao;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import domain.Account;
import domain.Operation;
import entity.AccountEntity;
import entity.OperationEntity;
import exception.MonetaryException;
import mapper.Mapper;
import service.OperationService;

import java.util.List;
import java.util.stream.Collectors;

public class OperationServiceImpl implements OperationService {

    private final OperationDao operationDao;
    private final AccountDao accountDao;
    private final Mapper<Operation, OperationEntity> mapper;
    private final Mapper<Account, AccountEntity> accountMapper;

    public OperationServiceImpl(OperationDao operationDao, AccountDao accountDao, Mapper<Operation, OperationEntity> mapper, Mapper<Account, AccountEntity> accountMapper) {
        this.operationDao = operationDao;
        this.accountDao = accountDao;
        this.mapper = mapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public void save(Operation operation) {
        Account sender = accountDao.findById(operation.getId())
                .map(accountMapper::mapEntityToDomain)
                .orElseThrow();

        Account receiver = accountDao.findById(operation.getId())
                .map(accountMapper::mapEntityToDomain)
                .orElseThrow();

        if (sender.getBalance() < operation.getTransfer()) {
            throw new MonetaryException("Not enough balance to perform such operation");
        }

        sender.processTransfer(operation);
        receiver.processTransfer(operation);

        operationDao.save(mapper.mapDomainToEntity(operation), accountMapper.mapDomainToEntity(sender), accountMapper.mapDomainToEntity(receiver));
    }

    @Override
    public Pageable<Operation> findAllOperationsByAccountId(Integer id, Page page) {
        Pageable<OperationEntity> allByAccountId = operationDao.findAllByAccountId(id, page);

        List<Operation> operations = allByAccountId.getItems()
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());

        return new Pageable<>(operations,allByAccountId.getPageNumber(),allByAccountId.getItemsNumberPerPage(),allByAccountId.getCountAll());
    }
}
