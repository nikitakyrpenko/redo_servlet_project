package dao.util.consumer;

import java.sql.SQLException;

@FunctionalInterface
public interface BiConsumer<T,U> {
    void accept(T t, U u) throws SQLException;

}
