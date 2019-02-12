package am.aca.dbmigration.dataMigrator;

import am.aca.dbmigration.sql.tables.MySQLTable;
import am.aca.dbmigration.sql.utils.JdbcUrlHelper;
import am.aca.dbmigration.sql.tables.PostgreSQLTable;

public class DataMigratorFactory {

    public static DataMigrator getDataMigrator(String urlFrom, String urlTo , String usernameFrom, String passwordFrom){

        String dbFrom = JdbcUrlHelper.getDbType(urlFrom);
        String dbTo = JdbcUrlHelper.getDbType(urlTo);
        switch (dbFrom) {
            case "postgresql": {
                switch (dbTo) {
                    case "mysql":
                        return new DataMigrator<MySQLTable>(urlFrom, usernameFrom, passwordFrom );
                    default:
                        throw new UnsupportedOperationException("Unsupported RDBMS");
                }
            }

            case "mysql": {
                switch (dbTo) {
                    case "postgresql":
                        return new DataMigrator<PostgreSQLTable>(urlFrom, usernameFrom, passwordFrom);
                    default:
                        throw new UnsupportedOperationException("Unsupported RDBMS");
                }
            }

            /*case "oracle": {}
            case "sqlserver":{}
            */
            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }

}
