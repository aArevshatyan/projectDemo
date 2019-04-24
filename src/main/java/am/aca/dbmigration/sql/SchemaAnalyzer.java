package am.aca.dbmigration.sql;

import am.aca.dbmigration.analyzers.DDLAnalyzerFactory;
import am.aca.dbmigration.converters.ConverterFactory;
import am.aca.dbmigration.dataMigrator.DataMigratorFactory;
import am.aca.dbmigration.generators.GeneratorFactory;
import am.aca.dbmigration.sql.generatedSQLs.UnsupportedFeatures;
import am.aca.dbmigration.sql.tables.Table;

import java.sql.SQLException;
import java.util.List;

/**
 * Responsible for manipulating the schema
 */
public class SchemaAnalyzer {

    /**
     * It takes the source database information and by analyzing them creates a schema.
     *
     * @param urlFrom      of the source database
     * @param usernameFrom of the source database
     * @param passwordFrom of the source database
     * @return list of source database type tables
     * @throws SQLException
     */
    public static void getSchema(String urlFrom, String usernameFrom, String passwordFrom)
            throws SQLException {
        MigrationData.schemaFrom = DDLAnalyzerFactory.
                getAnalyzer(urlFrom, usernameFrom, passwordFrom)
                .getSchema();
    }

    /**
     * Set selected tables to enabled, making them ready to migrate
     *
     * @param enabledTableNames user's selected table names
     */
    public static void setEnabled(String enabledTableNames) {
        String[] enabledTables = enabledTableNames.split(",");
        for (String enabledTable : enabledTables) {
            for (Table table : MigrationData.schemaFrom.getTables()) {
                if (table.getName().equalsIgnoreCase(enabledTable)) {
                    table.setEnabled(true);
                    break;
                }
            }
        }
    }

    /**
     * @return list of warning messages about not supported features
     * and unable foreign keys
     * @throws SQLException
     */
    public static List<String> generateSqls() throws SQLException {
        MigrationData.schemaTo =
                ConverterFactory
                        .getConverter(MigrationData.urlFrom, MigrationData.urlTo)
                        .convert(MigrationData.schemaFrom);
        GeneratorFactory
                .getGenerator(MigrationData.urlTo)
                .generateSQLOf(MigrationData.schemaTo);
        DataMigratorFactory.getDataMigrator(
                MigrationData.urlFrom,
                MigrationData.urlTo,
                MigrationData.usernameFrom,
                MigrationData.passwordFrom)
                .generateMigrationSQL(MigrationData.schemaTo);
        return UnsupportedFeatures.getUnsupportedFeatures();
    }
}