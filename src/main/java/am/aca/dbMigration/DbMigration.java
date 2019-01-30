package am.aca.dbMigration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.components.utils.DoDbWork;
import am.aca.components.utils.UnsupportedFeatures;
import am.aca.dataMigrator.*;
import am.aca.generators.GeneratorFactory;
import am.aca.converters.ConverterFactory;
import am.aca.analyzers.DDLAnalyzerFactory;
import am.aca.components.utils.JdbcUrlHelper;

public class DbMigration{

    public static void main(String[] args) throws SQLException {

        String usernameFrom = "root";
//        String usernameFrom = "postgres";
//        String usernameTo = "root";
        String usernameTo = "postgres";
        String passwordFrom = "root";
        String passwordTo = "root";


//        String jdbcMySQLUrl = "jdbc:mysql://aca-db.duckdns.org:3306/do_not_touch";
        String jdbcMySQLUrl = "jdbc:mysql://localhost:3306/test2";
//        String jdbcMySQLUrl = "jdbc:mysql://localhost:3306/yuhu";


//        String jdbcPostgreSqlUrl = "jdbc:postgresql://aca-db.duckdns.org:5432/postgres";
        String jdbcPostgreSqlUrl = "jdbc:postgresql://localhost:5432/test2";
//
        Schema mySQLSchema = DDLAnalyzerFactory.
                getAnalyzer(jdbcMySQLUrl, usernameFrom, passwordFrom)
                .getSchema();
//        Schema postgreSQLSchema = DDLAnalyzerFactory
//                .getAnalyzer(jdbcPostgreSqlUrl, usernameFrom, passwordFrom)
//                .getSchema();

        Schema mySQLSchemaAfterSelected = userSelectedTables(mySQLSchema, jdbcMySQLUrl);
//        Schema postgreSQLSchemaAfterSelected = userSelectedTables(postgreSQLSchema, jdbcPostgreSqlUrl);

        Schema convertedMyToPostgres = ConverterFactory
                .getConverter(jdbcMySQLUrl, jdbcPostgreSqlUrl)
                .convert(mySQLSchemaAfterSelected);
//        Schema convertedPostgresToMy = ConverterFactory
//                .getConverter(jdbcPostgreSqlUrl, jdbcMySQLUrl)
//                .convert(postgreSQLSchemaAfterSelected);

        GeneratorFactory
                .getGenerator(jdbcPostgreSqlUrl)
                .generateSQLOf(convertedMyToPostgres);
//        GeneratorFactory
//                .getGenerator(jdbcMySQLUrl)
//                .generateSQLOf(convertedPostgresToMy );

//        DataMigratorFactory.getDataMigrator(jdbcMySQLUrl, jdbcPostgreSqlUrl).generateMigrationSQL(mySQLSchemaAfterSelected, convertedMyToPostgres);
//        DataMigratorFactory.getDataMigrator(jdbcPostgreSqlUrl,jdbcMySQLUrl, usernameFrom,passqordFrom).generateMigrationSQL(postgreSQLSchemaAfterSelected);

//        DataMigratorFactory.getDataMigrator(jdbcPostgreSqlUrl,jdbcMySQLUrl,usernameFrom,passwordFrom).generateMigrationSQL(postgreSQLSchemaAfterSelected);
        DataMigratorFactory.getDataMigrator(jdbcMySQLUrl,jdbcPostgreSqlUrl,usernameFrom,passwordFrom).generateMigrationSQL(mySQLSchemaAfterSelected);

        System.out.println(UnsupportedFeatures.getUnsupportedFeatures());

        //popupa galis ete yndunuma migrate enq anum

        DoDbWork.migrate(jdbcPostgreSqlUrl, usernameTo, passwordTo);
//        DoDbWork.migrate(jdbcMySQLUrl, usernameTo, passwordTo);

    }



    private static <T> Schema<T> userSelectedTables(Schema<T> schema, String url) {

        String dbType = JdbcUrlHelper.getDbType(url);

        Scanner sc = new Scanner(System.in);
        System.out.println("Select table index for migrating: ");
        String indexes;
        int index = 0;

        switch (dbType) {
            case "mysql":
                for (T table : schema.getTables()) {
                    System.out.println("\t" + index++ + " " + ((MySQLTable) table).getName());
                }
                indexes = sc.nextLine();
                List<Integer> indexM = Arrays
                        .stream(indexes.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                indexM
                        .forEach(i -> ((MySQLTable)schema.getTables().get(i)).setEnabled(true));

                return schema;
            case "postgresql":
                for (T table : schema.getTables()) {
                    System.out.println("\t" + index++ + " " + ((PostgreSQLTable) table).getName());
                }
                indexes = sc.nextLine();
                List<Integer> indexP = Arrays
                        .stream(indexes.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                indexP
                        .forEach(i -> ((PostgreSQLTable)schema.getTables().get(i)).setEnabled(true));
                return schema;
            default:
                return null;
        }
    }
}
