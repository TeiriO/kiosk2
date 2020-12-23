package dao.db;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final Properties credentials;
    private final Properties sql;

    private final String PATH_TO_CONFIG = "src/main/resources/credentials.properties";
    private final String PATH_TO_SQL = "src/main/resources/sql.properties";

    private DBConnection() {
        credentials = new Properties();
        sql = new Properties();
        try {
            credentials.load(new FileReader(PATH_TO_CONFIG));
            sql.load(new FileReader(PATH_TO_SQL));
            Class.forName(credentials.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            createDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        return new DBConnection();
    }

//    private boolean isDatabaseExist() throws SQLException {
//        var isExist = false;
//        try (
//            var connection = DriverManager.getConnection(
//                credentials.getProperty("host"),
//                credentials.getProperty("login"),
//                credentials.getProperty("password"));
//            var ps = connection.prepareStatement(sql.getProperty("GET_LIST_DATABASES")
//            );
//            var rs = ps.executeQuery()) {
//            while (rs.next()) {
//                if (rs.getString(1).equals(credentials.getProperty("db_name")))
//                    isExist = true;
//            }
//        }
//        return isExist;
//    }

    // if db doesnt exist
    private void createDatabase() throws SQLException {
        var connection = DriverManager.getConnection(
            credentials.getProperty("host"),
            credentials.getProperty("login"),
            credentials.getProperty("password"));

//        connection.prepareStatement(sql.getProperty("create_db")).executeUpdate();
    }

    private void createTables(Connection connection) throws SQLException {
        //create users
        var stm = connection.prepareStatement(sql.getProperty("CREATE_TABLE_USERS"));
        stm.executeUpdate();

        //create records
        stm = connection.prepareStatement(sql.getProperty("CREATE_TABLE_RECORDS"));
        stm.executeUpdate();

        //create numbers
        stm = connection.prepareStatement(sql.getProperty("CREATE_TABLE_NUMBERS"));
        stm.executeUpdate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            credentials.getProperty("host_name"),
            credentials.getProperty("login"),
            credentials.getProperty("password"));
    }
}
