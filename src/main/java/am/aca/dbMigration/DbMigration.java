package am.aca.dbMigration;

import am.aca.components.Schema;
import am.aca.analyzers.DDLAnalyzerFactory;

import java.sql.SQLException;

public class DbMigration {

    private static class JdbcUrlHelper {
        public static String getDbType(String jdbcUrl) {
            int firstIndex = jdbcUrl.indexOf(":") + 1;
            return jdbcUrl.substring(firstIndex, jdbcUrl.indexOf(":", firstIndex));
        }
    }

    public static void main(String[] args) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/test2";
        //String jdbcUrl = "jdbc:postgresql://localhost:5432/test2";
        Schema schema = DDLAnalyzerFactory
                .getAnalyzer(JdbcUrlHelper
                        .getDbType(jdbcUrl))
                .getSchemaOf("jdbc:mysql://localhost:3306/test2");
        schema
                .getTables()
                .forEach(x -> {
                            System.out.println("Table : " + x.getName());
                            System.out.println("Columns :");
                            x
                                    .getColumns()
                                    .forEach(y -> System.out.println("\t" + y.getField()))
                            ;
                            System.out.println("Constraints :");
                            x
                                    .getConstraints()
                                    .forEach(z -> System.out.println("\t" + z.getConstraint()))
                            ;
                            System.out.println();
                        }
                )
        ;
    }

}

