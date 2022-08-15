package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "testuser", "testpassword");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        setUp();
        var sqlQueryToDeleteCodes = "DELETE FROM auth_codes;";
        var sqlQueryToDeleteCards = "DELETE FROM cards;";
        var sqlQueryToDeleteUsers = "DELETE FROM users;";
        runner.update(conn, sqlQueryToDeleteCodes);
        runner.update(conn, sqlQueryToDeleteCards);
        runner.update(conn, sqlQueryToDeleteUsers);
    }

    @SneakyThrows
    public static String getCode(String login) {
        setUp();
        var sqlQueryForCode = "SELECT code FROM auth_codes JOIN users ON user_id = users.id WHERE users.login=\"" + login + "\" ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, sqlQueryForCode, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static String getStatus(String login) {
        setUp();
        var sqlQueryForStatus = "SELECT status FROM users WHERE users.login=\"" + login + "\";";
        return runner.query(conn, sqlQueryForStatus, new ScalarHandler<String>());
    }

}
