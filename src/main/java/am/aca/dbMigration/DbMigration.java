package am.aca.dbMigration;

import java.sql.SQLException;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.converters.ConverterFactory;
import am.aca.analyzers.DDLAnalyzerFactory;
import am.aca.components.utils.JdbcUrlHelper;

public class DbMigration {


    /*Todo
            genericacnel
            data lcnel
            sqlnery generacnel
            karch asac verjnakan tesqi berel minchev batch sqly
     */

    public static void main(String[] args) throws SQLException {
        String jdbcMySQLUrl = "jdbc:mysql://aca-db.duckdns.org:3306/do_not_touch";
        String jdbcPostgreSqlUrl = "jdbc:postgresql://aca-db.duckdns.org:5432/postgres";


        String mySQLType = JdbcUrlHelper.getDbType(jdbcMySQLUrl);
        String postgreSQLType = JdbcUrlHelper.getDbType(jdbcPostgreSqlUrl);

        Schema mySQLSchema = DDLAnalyzerFactory
                .getAnalyzer(mySQLType)
                .getSchemaOf(jdbcMySQLUrl);
        Schema postgreSQLSchema = DDLAnalyzerFactory
                .getAnalyzer(postgreSQLType)
                .getSchemaOf(jdbcPostgreSqlUrl);


        Schema<PostgreSQLTable> convertedMyToPostgres = ConverterFactory.getConverter(mySQLType, postgreSQLType).convert(mySQLSchema);
        Schema<MySQLTable> convertedPostgresToMy = ConverterFactory.getConverter(postgreSQLType, mySQLType).convert(postgreSQLSchema);

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

        for (Object o : convertedMyToPostgres.getTables()) {
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

