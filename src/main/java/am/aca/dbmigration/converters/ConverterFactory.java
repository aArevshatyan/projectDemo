package am.aca.dbmigration.converters;

import am.aca.dbmigration.sql.utils.JdbcUrlHelper;

public class ConverterFactory {

    public static Converter getConverter(String urlFrom, String urlTo) {

        String from = JdbcUrlHelper.getDbType(urlFrom);
        String to = JdbcUrlHelper.getDbType(urlTo);

        switch (from) {
            case "postgresql": {
                switch (to) {
                    case "mysql":
                        return new PostgreSQLToMySQL();
                    default:
                        throw new UnsupportedOperationException("Unsupported RDBMS");
                }
            }

            case "mysql": {
                switch (to) {
                    case "postgresql":
                        return new MySQLToPostgreSql();
                    default:
                        throw new UnsupportedOperationException("Unsupported RDBMS");
                }
            }

            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }
}
