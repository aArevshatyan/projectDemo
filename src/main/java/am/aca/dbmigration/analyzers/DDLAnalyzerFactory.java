package am.aca.dbmigration.analyzers;

import am.aca.dbmigration.sql.tables.Table;
import am.aca.dbmigration.sql.utils.JdbcUrlHelper;

public class DDLAnalyzerFactory {

    /**
     * It takes the JDBC url extracts the database type and specifies the DDLAnalyzer
     *
     * @param url is the JDBC url of source database
     * @param username of the source database
     * @param password of the source database
     * @return Specific type DDLAnalyzer based on source database information
     */
    public static DDLAnalyzer<? extends Table> getAnalyzer(String url, String username, String password) {

        String type = JdbcUrlHelper.getDbType(url);

        switch (type) {
            case "mysql":
                return new MySQLDDLAnalyzer(url, username, password);
            case "postgresql":
                return new PostgreSQLDDLAnalyzer(url, username, password);
            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }

}

