package controllers;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;

public class DB {
    protected static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection != null) {
                return connection;
            }
            MysqlDataSource source = new MysqlDataSource();
            source.setUser("root");
            source.setPassword("root");
            source.setDatabaseName("justask");
            source.setPort(3306);
            source.setServerName("localhost");

            connection = source.getConnection();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
}
