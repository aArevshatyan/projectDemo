package am.aca.dbmigration.sql.utils;

import am.aca.dbmigration.sql.generatedSQLs.GeneratedCreateSQLs;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedForeignSQls;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedInsertSQLs;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedPrimarySQLs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DoDbWork {

    public static void migrate(String urlTo, String usernameTo, String passwordTo) throws SQLException {

        Connection connection = DriverManager.getConnection(
                urlTo,
                usernameTo,
                passwordTo
        );

        Statement statement = connection.createStatement();

        for (String s : GeneratedCreateSQLs.getCreateSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        for (String s : GeneratedPrimarySQLs.getPrimarySQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        for (String s : GeneratedInsertSQLs.getGeneratedInsertSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        for (String s : GeneratedForeignSQls.getForeignSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();

    }
}
