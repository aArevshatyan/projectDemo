package am.aca.dbMigration;

import am.aca.components.Schema;
import am.aca.components.MySQLTable;
import am.aca.components.PostgreSQLTable;
import am.aca.analyzers.DDLAnalyzerFactory;

import java.sql.SQLException;

public class DbMigration {

    private static class JdbcUrlHelper {
        static String getDbType(String jdbcUrl) {
            int firstIndex = jdbcUrl.indexOf(":") + 1;
            return jdbcUrl.substring(firstIndex, jdbcUrl.indexOf(":", firstIndex));
        }
    }

    public static void main(String[] args) throws SQLException {
        String jdbcMySQLUrl = "jdbc:mysql://localhost:3306/test2";
        String jdbcPostgreSqlUrl = "jdbc:postgresql://localhost:5432/test2";
        Schema mySQLSchema = DDLAnalyzerFactory
                .getAnalyzer(JdbcUrlHelper
                        .getDbType(jdbcMySQLUrl))
                .getSchemaOf(jdbcMySQLUrl);

        Schema postgreSQLSchema = DDLAnalyzerFactory
                .getAnalyzer(JdbcUrlHelper
                        .getDbType(jdbcPostgreSqlUrl))
                .getSchemaOf(jdbcPostgreSqlUrl);

        for (Object o : mySQLSchema.getTables()) {
            System.out.println(o);
            for (Object o1 : ((MySQLTable) o).getColumns()) {
                System.out.println("\t" + o1);
            }
            for (Object o1 : ((MySQLTable) o).getConstraints()) {
                System.out.println("\t\t" + o1);
            }
        }

        System.out.println("-------------");

        for (Object o : postgreSQLSchema.getTables()) {
            System.out.println(o);
            for (Object o1 : ((PostgreSQLTable) o).getColumns()) {
                System.out.println("\t" + o1);
            }
            for (Object o1 : ((PostgreSQLTable) o).getConstraints()) {
                System.out.println("\t\t" + o1);
            }
        }

    }

}

