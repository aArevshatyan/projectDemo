package am.aca.analyzers;

public class DDLAnalyzerFactory {

    public static DDLAnalyzer getAnalyzer(String type) {
        switch (type) {
            case "mysql":
                return new MySQLDDLAnalyzer();
            case "postgresql":
                return new PostgreSQLDDLAnalyzer();

            /*case "oracle":
                return new OracleDDLAnalyzer();
            case "sqlserver":
                return new SQLServerDDLAnalyzer();*/

            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }

}

