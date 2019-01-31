package am.aca.backWork.components.utils;

import am.aca.backWork.components.generatedSQLs.GeneratedCreateSQLs;
import am.aca.backWork.components.generatedSQLs.GeneratedForeignSQls;
import am.aca.backWork.components.generatedSQLs.GeneratedInsertSQLs;
import am.aca.backWork.components.generatedSQLs.GeneratedPrimarySQLs;

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
