package dao.impl;

import dao.AbstractCrudDaoImpl;
import dao.AccountDao;
import dao.OperationDao;
import dao.util.ConnectorDB;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.AccountEntity;
import entity.OperationEntity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OperationCrudDaoImpl extends AbstractCrudDaoImpl<OperationEntity> implements OperationDao {
    private static Logger LOGGER = Logger.getLogger(OperationCrudDaoImpl.class);

    private final AccountDao accountDao;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM operations WHERE operation_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM operations";
    private static final String FIND_ALL_PAGEABLE_QUERY = "SELECT * FROM operations LIMIT ? , ?";
    private static final String COUNT_ALL_QUERY = "SELECT count(*) FROM operations";
    private static final String DELETE_BY_ID = "DELETE FROM accounts WHERE operation_id = ?";
    private static final String FIND_ALL_PAGEABLE_BY_ACCOUNT = "SELECT * FROM operations WHERE fk_accounts_receiver = ? OR fk_accounts_sender = ? LIMIT ?,?";
    private static final String COUNT_ALL_BY_ACCOUNT_ID = "SELECT count(*) FROM operations WHERE fk_accounts_receiver = ? OR fk_accounts_sender = ?";
    private static final String INSERT_QUERY = "INSERT INTO operations ( operation_date, purpose, transfer, fk_accounts_receiver, fk_accounts_sender) VALUES ( ?, ?, ?, ?, ?)";

    public OperationCrudDaoImpl(ConnectorDB connector, AccountDao accountDao) {
        super(connector, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGEABLE_QUERY, COUNT_ALL_QUERY, DELETE_BY_ID);
        this.accountDao = accountDao;
    }

    @Override
    public Pageable<OperationEntity> findAllByAccountId(Integer id, Page page) {
        return super.findAllByParamPageable(id, page, FIND_ALL_PAGEABLE_BY_ACCOUNT, COUNT_ALL_BY_ACCOUNT_ID,
                (preparedStatement, param, Page) -> {
                    int pageOffset = page.getPageNumber() * page.getItemsPerPage();
                    int pageSize = page.getItemsPerPage();

                    preparedStatement.setObject(1, param);
                    preparedStatement.setObject(2, param);
                    preparedStatement.setObject(3, pageOffset);
                    preparedStatement.setObject(4, pageSize);
                },
                ((preparedStatement, integer) -> {
                    preparedStatement.setObject(1,integer);
                    preparedStatement.setObject(2,integer);
                }));
    }

    @Override
    public void save(OperationEntity operationEntity, AccountEntity sender, AccountEntity receiver) {
        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            mapOperationEntityToResultSet(operationEntity);
            accountDao.update(sender);
            accountDao.update(receiver);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void save(OperationEntity entity) {

        mapOperationEntityToResultSet(entity);

    }

    private void mapOperationEntityToResultSet(OperationEntity entity) {
        try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_QUERY)) {
            statement.setObject(1, entity.getDateOfTransaction());
            statement.setObject(2, entity.getPurposeOfTransaction());
            statement.setObject(3, entity.getTransfer());
            statement.setObject(4, entity.getReceiverOfTransaction());
            statement.setObject(5, entity.getSenderOfTransaction());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void update(OperationEntity entity) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    protected OperationEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        OperationEntity operationEntity = null;
        try {
            operationEntity = OperationEntity.builder()
                    .withId(resultSet.getInt("operation_id"))
                    .withPurpose(resultSet.getString("purpose"))
                    .withTransfer(resultSet.getDouble("transfer"))
                    .withDate(resultSet.getDate("operation_date"))
                    .withReceiver(resultSet.getInt("fk_accounts_receiver"))
                    .withSender(resultSet.getInt("fk_accounts_sender"))
                    .build();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return operationEntity;
    }

}
