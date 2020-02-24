package dao;

import dao.exception.DataBaseSqlRuntimeException;
import dao.util.ConnectorDB;
import dao.util.consumer.BiConsumer;
import dao.util.consumer.ParamPageableConsumer;
import dao.util.pages.Page;
import dao.util.pages.Pageable;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class AbstractCrudDaoImpl<E> implements CrudPageableDao<E> {
    private static Logger LOGGER = Logger.getLogger(AbstractCrudDaoImpl.class);

    protected final ConnectorDB connector;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String findAllPageableQuery;
    private final String countAllQuery;
    private final String deleteByIdQuery;


    protected final BiConsumer<PreparedStatement, Integer> INT_PARAM_SETTER =
            ((statement, integer) -> statement.setObject(1, integer));

    protected final ParamPageableConsumer<PreparedStatement, Integer, Page> INT_PAGE_PARAM_SETTER =
            (preparedStatement, param, page) -> {
                int pageOffset = page.getPageNumber() * page.getItemsPerPage();
                int pageSize = page.getItemsPerPage();

                preparedStatement.setObject(1, param);
                preparedStatement.setObject(2, pageOffset);
                preparedStatement.setObject(3, pageSize);
            };

    protected AbstractCrudDaoImpl(ConnectorDB connector,
                                  String findByIdQuery,
                                  String findAllQuery,
                                  String findAllPageableQuery,
                                  String countAllQuery,
                                  String deleteById) {
        this.connector = connector;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.findAllPageableQuery = findAllPageableQuery;
        this.countAllQuery = countAllQuery;
        this.deleteByIdQuery = deleteById;
    }

    @Override
    public Pageable<E> findAll(Page page) {
        List<E> result = new ArrayList<>();

        int pageOffset = page.getPageNumber() * page.getItemsPerPage();
        int pageSize = page.getItemsPerPage();

        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findAllPageableQuery)) {
            preparedStatement.setInt(1, pageOffset);
            preparedStatement.setInt(2, pageSize);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return new Pageable<>(result, page.getPageNumber(), page.getItemsPerPage(), countAll().get());
    }

   /* @Override
    public Pageable<E> findAllByParam(Integer id, Page page) {
        int pageOffset = page.getPageNumber() * page.getItemsPerPage();
        int pageSize = page.getItemsPerPage();

        List<E> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findAllPageableByPrimaryKey)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageOffset);
            preparedStatement.setInt(3,pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
        LOGGER.error(e.getMessage());
    }
        return new Pageable<>(result, page.getPageNumber(), page.getItemsPerPage(), 10);
    }*/


    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, findByIdQuery, INT_PARAM_SETTER);
    }

    @Override
    public List<E> findAll() {
        List<E> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }
        return result;
    }


    @Override
    public void deleteById(Integer id) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(deleteByIdQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }
    }

    @Override
    public Optional<Integer> countAll() {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(countAllQuery)) {

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }

        return Optional.empty();
    }

    protected <P> Optional<E> findByParam(P param, String findByParam, BiConsumer<PreparedStatement, P> designatedParamSetter) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findByParam)) {
            designatedParamSetter.accept(preparedStatement, param);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }

        return Optional.empty();
    }

    protected <P> Pageable<E> findAllByParamPageable(Integer param,
                                                     Page page,
                                                     String findByParamPageable,
                                                     String countAllByParam,
                                                     ParamPageableConsumer<PreparedStatement, Integer, Page> designatedParamSetter,
                                                     BiConsumer<PreparedStatement, Integer> countParamSetter) {
        List<E> result = new ArrayList<>();

        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findByParamPageable)) {
            designatedParamSetter.accept(preparedStatement, param, page);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapResultSetToEntity(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }

         return new Pageable<E>(result, page.getPageNumber(), page.getItemsPerPage(), countAllByParam(param, countAllByParam,countParamSetter).get());
    }

    protected Optional<Integer> countAllByParam(Integer param, String countByParamQuery, BiConsumer<PreparedStatement, Integer> designatedParamSetter) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(countByParamQuery)) {
            designatedParamSetter.accept(preparedStatement,param);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseSqlRuntimeException("Database exception occurred", e);
        }

        return Optional.empty();
    }


    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
