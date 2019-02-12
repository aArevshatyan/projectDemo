package am.aca.dbmigration.analyzers;

import am.aca.dbmigration.sql.tables.Table;
import am.aca.dbmigration.sql.utils.JdbcUrlHelper;

public class DDLAnalyzerFactory {

    public static DDLAnalyzer<? extends Table> getAnalyzer(String url, String username, String password) {

        String type = JdbcUrlHelper.getDbType(url);

        switch (type) {
            case "mysql":
                return new MySQLDDLAnalyzer(url, username, password);
            case "postgresql":
                return new PostgreSQLDDLAnalyzer(url, username, password);

            /*case "oracle":
                return new OracleDDLAnalyzer();
            case "sqlserver":
                return new SQLServerDDLAnalyzer();*/

            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }

}

