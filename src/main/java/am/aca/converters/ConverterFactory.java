package am.aca.converters;

public class ConverterFactory {

    public static Converter getConverter(String from, String to) {

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

            /*case "oracle": {}
            case "sqlserver":{}
            */
            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }
}
