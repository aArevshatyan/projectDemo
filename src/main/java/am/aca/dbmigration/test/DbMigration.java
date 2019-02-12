package am.aca.dbmigration.test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.tables.*;
import am.aca.dbmigration.sql.utils.DoDbWork;
import am.aca.dbmigration.sql.utils.UnsupportedFeatures;
import am.aca.dbmigration.dataMigrator.*;
import am.aca.dbmigration.generators.GeneratorFactory;
import am.aca.dbmigration.converters.ConverterFactory;
import am.aca.dbmigration.analyzers.DDLAnalyzerFactory;
import am.aca.dbmigration.sql.utils.JdbcUrlHelper;

//TODO delete
public class DbMigration{

    public static void test(String urlFrom, String usernameFrom, String passwordFrom,
                            String urlTo, String usernameTo, String passwordTo) throws SQLException {



        Schema<? extends Table> mySQLSchema = DDLAnalyzerFactory.
                getAnalyzer(urlFrom, usernameFrom, passwordFrom)
                .getSchema();



//        String jdbcMySQLUrl = "jdbc:mysql://aca-db.duckdns.org:3306/do_not_touch";
        String jdbcMySQLUrl = "jdbc:mysql://localhost:3306/test2";
//        String jdbcMySQLUrl = "jdbc:mysql://localhost:3306/yuhu";


//        String jdbcPostgreSqlUrl = "jdbc:postgresql://aca-db.duckdns.org:5432/postgres";
        String jdbcPostgreSqlUrl = "jdbc:postgresql://localhost:5432/test2";


        Schema<? extends Table> mySQLSchemaAfterSelected = userSelectedTables(mySQLSchema, jdbcMySQLUrl);
//        Schema postgreSQLSchemaAfterSelected = userSelectedTables(postgreSQLSchema, jdbcPostgreSqlUrl);

        Schema convertedMyToPostgres = ConverterFactory.getConverter(jdbcMySQLUrl, jdbcPostgreSqlUrl).convert(mySQLSchemaAfterSelected);
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



    private static <T extends Table> Schema<T> userSelectedTables(Schema<T> schema, String url) {

        String dbType = JdbcUrlHelper.getDbType(url);

        Scanner sc = new Scanner(System.in);
        System.out.println("Select table index for migrating: ");
        String indexes;
        int index = 0;

        switch (dbType) {
            case "mysql":
                for (T table : schema.getTables()) {
                    System.out.println("\t" + index++ + " " + table.getName());
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
                    System.out.println("\t" + index++ + " " + table.getName());
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
