package am.aca.backWork.generators;

import am.aca.backWork.components.utils.JdbcUrlHelper;

public class GeneratorFactory {
    public static Generator getGenerator(String url) {

        String type = JdbcUrlHelper.getDbType(url);

        switch (type) {
            case "mysql":
                return new MySQLGenarator();
            case "postgresql":
                return new PostgreSQLGenerator();
            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }

            /*case "oracle": {}
            case "sqlserver":{}
            */
    }
}
