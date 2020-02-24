package dao.impl;

import dao.AbstractCrudDaoImpl;
import dao.UserDao;
import dao.util.ConnectorDB;
import entity.UserEntity;
import enums.Role;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserCrudDaoImpl extends AbstractCrudDaoImpl<UserEntity> implements UserDao {
    private static Logger LOGGER = Logger.getLogger(ChargeCrudDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_ALL_PAGEABLE_QUERY = "SELECT * FROM users LIMIT ?, ?";
    private static final String COUNT_ALL_QUERY = "SELECT count(*) FROM users";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE user_id = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (`firstname`,`surname`,`email`,`passwords`,`telephone`,`fk_roles_users`) VALUES (?,?,?,?,?,?)";

    public UserCrudDaoImpl(ConnectorDB connector) {
        super(connector, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGEABLE_QUERY, COUNT_ALL_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        Role role = resultSet.getInt("fk_roles_users") == 1 ? Role.CLIENT : Role.ADMIN;

        return UserEntity.builder()
                .withId(resultSet.getInt("user_id"))
                .withEmail(resultSet.getString("email"))
                .withName(resultSet.getString("firstname"))
                .withSurname(resultSet.getString("surname"))
                .withPassword(resultSet.getString("password"))
                .withTelephone(resultSet.getString("telephone"))
                .withRole(role)
                .build();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return super.findByParam(email, FIND_BY_EMAIL_QUERY, (preparedStatement, s) -> preparedStatement.setObject(1, email));
    }

    @Override
    public void save(UserEntity entity) {
        try (final PreparedStatement statement = connector.getConnection().prepareStatement(INSERT_USER_QUERY)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getTelephone());
            statement.setInt(6, entity.getRole().ordinal());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void update(UserEntity entity) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
