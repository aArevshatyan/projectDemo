package am.aca.generators;

public class GeneratorFactory {
    public static Generator getGenerator(String type) {

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
