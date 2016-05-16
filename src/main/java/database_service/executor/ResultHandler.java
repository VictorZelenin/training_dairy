package database_service.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by victor on 15.05.16.
 */
@FunctionalInterface
public interface ResultHandler<T> {

    T handle(ResultSet resultSet) throws SQLException;
}
