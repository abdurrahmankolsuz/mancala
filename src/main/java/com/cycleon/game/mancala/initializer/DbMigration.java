package com.cycleon.game.mancala.initializer;


import java.sql.*;

public class DbMigration {

    private final String dbURL = "jdbc:postgresql://localhost:5432/";
    private final String dbName = "mancala";
    private final String userName = "postgres";
    private final String password = "postgres";

    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL, userName, password);
        Statement statement = connection.createStatement();
        if (!dbExists(statement)) {
            createDb(statement);
        }
    }

    private boolean dbExists(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false;");
        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(dbName)) {
                return true;
            }
        }
        return false;
    }

    private void createDb(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE DATABASE " + dbName + " OWNER " + userName);
    }

}
