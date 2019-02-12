package am.aca.dbmigration.sql;

import am.aca.dbmigration.analyzers.DDLAnalyzerFactory;

import java.sql.SQLException;
import java.util.List;

public class SchemaAnalyzer {
    public static List getSchema(String urlFrom, String usernameFrom, String passwordFrom) throws SQLException {
        return DDLAnalyzerFactory.
                getAnalyzer(urlFrom, usernameFrom, passwordFrom)
                .getSchema().getTables();

    }

}
