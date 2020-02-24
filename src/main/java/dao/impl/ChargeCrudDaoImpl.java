package dao.impl;

import dao.AbstractCrudDaoImpl;
import dao.ChargeDao;
import dao.util.ConnectorDB;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.ChargeEntity;
import enums.ChargeType;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChargeCrudDaoImpl extends AbstractCrudDaoImpl<ChargeEntity> implements ChargeDao {
    private static Logger LOGGER = Logger.getLogger(ChargeCrudDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM charges WHERE charge_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM charges";
    private static final String FIND_ALL_PAGEABLE_QUERY = "SELECT * FROM charges LIMIT ? , ?";
    private static final String COUNT_ALL_QUERY = "SELECT count(*) FROM charges";
    private static final String DELETE_BY_ID = "DELETE FROM charges WHERE charge_id = ?";
    private static final String FIND_ALL_PAGEABLE_BY_ACCOUNT = "SELECT * FROM charges WHERE fk_account_charge = ? LIMIT ?,?";
    private static final String COUNT_ALL_BY_ACCOUNT_ID = "SELECT count(*) FROM charges WHERE fk_account_charge = ?";
    private static final String INSERT_QUERY = "INSERT INTO charges ( charge, fk_charge_types_charge, fk_account_charge) VALUES ( ?, ?, ?)";


    public ChargeCrudDaoImpl(ConnectorDB connector) {
        super(connector, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGEABLE_QUERY, COUNT_ALL_QUERY, DELETE_BY_ID);
    }

    @Override
    protected ChargeEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        Integer id = resultSet.getInt("charge_id");
        Integer account = resultSet.getInt("fk_account_charge");
        Double charge = resultSet.getDouble("charge");
        ChargeType chargeType = resultSet.getInt("fk_charge_types_charge") == 1 ? ChargeType.DEPOSIT_ARRIVAL : ChargeType.CREDIT_ARRIVAL;

        return new ChargeEntity(id, charge, chargeType, account);
    }

    @Override
    public Pageable<ChargeEntity> findAllByAccountId(Integer id, Page page) {
        return super.findAllByParamPageable(id, page, FIND_ALL_PAGEABLE_BY_ACCOUNT, COUNT_ALL_BY_ACCOUNT_ID, super.INT_PAGE_PARAM_SETTER, super.INT_PARAM_SETTER);
    }

    @Override
    public void save(ChargeEntity entity) {
        try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_QUERY)) {
            statement.setObject(1, entity.getCharge());
            statement.setObject(2, entity.getChargeType().ordinal());
            statement.setObject(3, entity.getAccountEntity());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void update(ChargeEntity entity) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
