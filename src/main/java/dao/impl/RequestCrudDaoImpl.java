package dao.impl;

import dao.AbstractCrudDaoImpl;
import dao.RequestDao;
import dao.util.ConnectorDB;
import entity.RequestEntity;
import enums.AccountType;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestCrudDaoImpl extends AbstractCrudDaoImpl<RequestEntity> implements RequestDao {
    private static Logger LOGGER = Logger.getLogger(RequestCrudDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM requests WHERE request_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM requests";
    private static final String FIND_ALL_PAGEABLE_QUERY = "SELECT * FROM requests LIMIT ? , ?";
    private static final String COUNT_ALL_QUERY = "SELECT count(*) FROM requests";
    private static final String DELETE_BY_ID = "DELETE FROM requests WHERE request_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO requests (fk_type_users, fk_users_request) VALUES ( ?, ?)";


    public RequestCrudDaoImpl(ConnectorDB connector) {
        super(connector, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGEABLE_QUERY, COUNT_ALL_QUERY, DELETE_BY_ID);
    }

    @Override
    protected RequestEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer request_id = resultSet.getInt("request_id");
        AccountType fk_type_users = resultSet.getInt("fk_type_users") == 1 ? AccountType.DEPOSIT : AccountType.CREDIT;
        Integer fk_users_request = resultSet.getInt("fk_users_request");

        return new RequestEntity(request_id, fk_type_users, fk_users_request);
    }

    @Override
    public void save(RequestEntity entity) {
        try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_QUERY)) {
            statement.setObject(1, entity.getAccountType());
            statement.setObject(2, entity.getUserId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void update(RequestEntity entity) {
        throw new UnsupportedOperationException("Unsopported operation");
    }
}
