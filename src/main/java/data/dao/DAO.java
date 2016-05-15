package data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by victor on 15.05.16.
 */
public abstract class DAO<T> {

    protected Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public abstract T get(long id);

    public abstract List<T> getAll();

    public abstract int save(T object);
    
    public abstract int delete(long id);

    public abstract int deleteAll();

    public Connection getConnection() {
        return connection;
    }

}
