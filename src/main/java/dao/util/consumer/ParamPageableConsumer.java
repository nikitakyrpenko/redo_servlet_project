package dao.util.consumer;

import java.sql.SQLException;

@FunctionalInterface
public interface ParamPageableConsumer<T,U,P> {
    void accept(T t, U param, P Page) throws SQLException;

}
