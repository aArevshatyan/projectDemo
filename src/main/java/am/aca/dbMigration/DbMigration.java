package am.aca.dbMigration;

import java.sql.SQLException;
import java.util.Scanner;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.generators.GeneratorFactory;
import am.aca.converters.ConverterFactory;
import am.aca.analyzers.DDLAnalyzerFactory;
import am.aca.components.utils.JdbcUrlHelper;

public class DbMigration {

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

        Schema mySQLSchemaAfterSelected = userSelectedTables(mySQLSchema, mySQLType);
        Schema postgreSQLSchemaAfterSelected = userSelectedTables(postgreSQLSchema, postgreSQLType);

        Schema convertedMyToPostgres = ConverterFactory
                .getConverter(mySQLType, postgreSQLType)
                .convert(mySQLSchemaAfterSelected);
        Schema convertedPostgresToMy = ConverterFactory
                .getConverter(postgreSQLType, mySQLType)
                .convert(postgreSQLSchemaAfterSelected);

        GeneratorFactory
                .getGenerator(postgreSQLType)
                .generateSQLOf(convertedMyToPostgres);
    }

    private static <T> Schema<T> userSelectedTables(Schema<T> schema, String dbType) {

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
                indexes = indexes.replaceAll(" ", "");
                for (int i = 0; i < indexes.length(); i++) {
                    int indexx = Character.getNumericValue(indexes.charAt(i));
                    Object mySQLTable = schema.getTables().get(indexx);
                    ((MySQLTable) mySQLTable).setEnabled(true);
                }
                return schema;
            case "postgresql":
                for (T table : schema.getTables()) {
                    System.out.println("\t" + index++ + " " + ((PostgreSQLTable) table).getName());
                }
                indexes = sc.nextLine();
                indexes = indexes.replaceAll(" ", "");
                for (int i = 0; i < indexes.length(); i++) {
                    int indexx = Character.getNumericValue(indexes.charAt(i));
                    Object postgreSQLTable = schema.getTables().get(indexx);
                    ((PostgreSQLTable) postgreSQLTable).setEnabled(true);
                }
                return schema;
            default:
                return null;
        }
    }
}

