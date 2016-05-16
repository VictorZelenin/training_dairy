package data.dao;

import data.data_sets.Client;
import data.enums.Gender;
import database_service.executor.Executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 15.05.16.
 */
public class ClientDAO extends DAO<Client> {

    private static final String SELECT_ALL = "select * from client";
    private static final String SELECT_BY_ID = "select * from client where id = ";
    private static final String INSERT = "INSERT INTO client(`name`, `login`, `password`, `gender`, " +
            "`age`, `weight` ) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "delete from client where id = ";
    private static final String DELETE_ALL = "delete from client";


    public ClientDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Client get(long id) {
        return Executor.executeQuery(connection, SELECT_BY_ID + id, resultSet -> {
            resultSet.next();
            return new Client(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), getGender(resultSet.getString(5)),
                    resultSet.getInt(6), resultSet.getDouble(7));
        });
    }

    private Gender getGender(String gender) {
        if (gender.toLowerCase().equals("man")
                || gender.toLowerCase().equals("мужской")
                || gender.toLowerCase().equals("чоловіча"))
            return Gender.MAN;
        else
            return Gender.WOMAN;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        Executor.executeQuery(connection, SELECT_ALL, result -> {

            while (result.next()) {
                clients.add(new Client(result.getLong("id"), result.getString("name"),
                        result.getString("login"), result.getString("password"),
                        getGender(result.getString("gender")), result.getInt("age"),
                        result.getDouble("weight")));
            }
            return clients;
        });

        return clients;
    }

    @Override
    public int save(Client client) {

        int updated = 0;

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getLogin());
            statement.setString(3, client.getPassword());
            statement.setString(4, getGender(client.getGender()));
            statement.setInt(5, client.getAge());
            statement.setDouble(6, client.getWeight());

            statement.execute();
            updated = statement.getUpdateCount();

        } catch (SQLException e) {
            System.err.println("Can not save!");
            e.printStackTrace();
        }


        return updated;
    }

    private String getGender(Gender gender) {
        if (gender == Gender.MAN) return "man";
        else return "woman";
    }

    @Override
    public int delete(long id) {
        return Executor.executeUpdate(connection, DELETE_BY_ID + id);
    }

    @Override
    public int deleteAll() {
        return Executor.executeUpdate(connection, DELETE_ALL);
    }


}
