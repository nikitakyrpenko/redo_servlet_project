package dao.impl;

import dao.AbstractCrudDaoImpl;
import dao.AccountDao;
import dao.util.ConnectorDB;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import entity.AccountEntity;
import entity.CreditAccountEntity;
import entity.DepositAccountEntity;
import enums.AccountType;
import org.apache.log4j.Logger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountCrudDaoImpl extends AbstractCrudDaoImpl<AccountEntity> implements AccountDao {
    private static Logger LOGGER = Logger.getLogger(AccountCrudDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM accounts WHERE account_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM accounts";
    private static final String FIND_ALL_PAGEABLE_QUERY = "SELECT * FROM accounts LIMIT ? , ?";
    private static final String COUNT_ALL_QUERY = "SELECT count(*) FROM accounts";
    private static final String FIND_ALL_PAGEABLE_BY_OWNER = "SELECT * FROM accounts WHERE fk_users_accounts = ? LIMIT ?,?";
    private static final String DELETE_BY_ID = "DELETE FROM accounts WHERE account_id = ?";
    private static final String INSERT_DEPOSIT_ACCOUNT_QUERY = "INSERT INTO accounts (`expiration_date`,`balance`,`deposit_account_rate`,`fk_users_accounts`,`fk_accounts_type_accounts`) VALUES (?,?,?,?,?)";
    private static final String INSERT_CREDIT_ACCOUNT_QUERY = "INSERT INTO accounts (`expiration_date`,`balance`,`credit_limit`,`credit_rate`,`charge_per_month`,`credit_liabilities`,`fk_users_accounts`,`fk_accounts_type_accounts`) VALUES (?,?,?,?,?,?,?,?)";
    private static final String COUNT_ALL_BY_OWNER_ID = "SELECT count(*) FROM accounts WHERE fk_users_accounts = ?";
    private static final String UPDATE_DEPOSIT_ACCOUNT = "UPDATE accounts SET expiration_date = ?, balance = ?, deposit_account_rate = ?, fk_users_accounts = ? WHERE account_id = ?";
    private static final String UPDATE_CREDIT_ACCOUNT = "UPDATE accounts SET expiration_date = ?, balance = ?, credit_limit = ?, credit_rate = ?, charge_per_month = ?, credit_liabilities= ?, fk_users_accounts = ?  WHERE account_id = ?";

    public AccountCrudDaoImpl(ConnectorDB connectorDB) {
        super(connectorDB, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGEABLE_QUERY,COUNT_ALL_QUERY, DELETE_BY_ID);
    }

    @Override
    protected AccountEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        AccountEntity accountEntityToReturn = null;
        try {
            AccountType accountType = resultSet.getInt("fk_accounts_type_accounts") == 1 ? AccountType.DEPOSIT : AccountType.CREDIT;
            if (accountType == AccountType.DEPOSIT) {
                accountEntityToReturn = new DepositAccountEntity.DepositAccountBuilder()
                        .withId(resultSet.getInt("account_id"))
                        .withHolder(resultSet.getInt("fk_users_accounts"))
                        .withDate(resultSet.getDate("expiration_date"))
                        .withDepositAmount(resultSet.getDouble("balance"))
                        .withDepositRate(resultSet.getDouble("deposit_account_rate"))
                        .withAccountType(accountType)
                        .build();
            } else {
                accountEntityToReturn = new CreditAccountEntity.CreditAccountBuilder()
                        .withId(resultSet.getInt("account_id"))
                        .withDate(resultSet.getDate("expiration_date"))
                        .withHolder(resultSet.getInt("fk_users_accounts"))
                        .withBalance(resultSet.getDouble("balance"))
                        .withCreditLimit(resultSet.getDouble("credit_limit"))
                        .withCreditRate(resultSet.getDouble("credit_rate"))
                        .withCreditCharge(resultSet.getDouble("charge_per_month"))
                        .withCreditLiability(resultSet.getDouble("credit_liabilities"))
                        .withAccountType(accountType)
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return accountEntityToReturn;
    }

    @Override
    public void save(AccountEntity entity) {
        if (entity.getAccountType() == AccountType.DEPOSIT) {
            try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_DEPOSIT_ACCOUNT_QUERY)) {
                DepositAccountEntity depositAccount = (DepositAccountEntity) entity;
                statement.setDate(1, new Date(depositAccount.getExpirationDate().getTime()));
                statement.setDouble(2, depositAccount.getBalance());
                statement.setDouble(3, depositAccount.getDepositRate());
                statement.setInt(4, depositAccount.getHolder());
                statement.setInt(5, AccountType.DEPOSIT.ordinal());
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_CREDIT_ACCOUNT_QUERY)) {
                CreditAccountEntity creditAccount = (CreditAccountEntity) entity;
                statement.setDate(1, new Date(creditAccount.getExpirationDate().getTime()));
                statement.setDouble(2, creditAccount.getBalance());
                statement.setDouble(3, creditAccount.getLimit());
                statement.setDouble(4, creditAccount.getRate());
                statement.setDouble(5, creditAccount.getCharge());
                statement.setDouble(6, creditAccount.getLiability());
                statement.setInt(7, creditAccount.getHolder());
                statement.setInt(8, AccountType.CREDIT.ordinal());
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Override
    public void update(AccountEntity entity) {
        if (entity.getAccountType() == AccountType.DEPOSIT) {
            DepositAccountEntity depositAccount = (DepositAccountEntity) entity;
            try (final PreparedStatement statement = connector.getConnection().prepareStatement(UPDATE_DEPOSIT_ACCOUNT)) {
                statement.setDate(1, new Date(depositAccount.getExpirationDate().getTime()));
                statement.setDouble(2, depositAccount.getBalance());
                statement.setDouble(3, depositAccount.getDepositRate());
                statement.setInt(4, entity.getHolder());
                statement.setInt(5, entity.getId());
                statement.executeUpdate();
            } catch (SQLException  e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            CreditAccountEntity creditAccount = (CreditAccountEntity) entity;
            try (final PreparedStatement statement = connector.getConnection().prepareStatement(UPDATE_CREDIT_ACCOUNT)) {
                statement.setDate(1, new Date(creditAccount.getExpirationDate().getTime()));
                statement.setDouble(2, creditAccount.getBalance());
                statement.setDouble(3, creditAccount.getLimit());
                statement.setDouble(4, creditAccount.getRate());
                statement.setDouble(5, creditAccount.getCharge());
                statement.setDouble(6, creditAccount.getLiability());
                statement.setInt(7, creditAccount.getHolder());
                statement.setInt(8, creditAccount.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Override
    public Pageable<AccountEntity> findAllByOwnerId(Integer id, Page page) {
        return super.findAllByParamPageable(id, page, FIND_ALL_PAGEABLE_BY_OWNER, COUNT_ALL_BY_OWNER_ID, super.INT_PAGE_PARAM_SETTER, super.INT_PARAM_SETTER);
    }


}
