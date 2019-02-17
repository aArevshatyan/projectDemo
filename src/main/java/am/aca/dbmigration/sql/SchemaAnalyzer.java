package am.aca.dbmigration.sql;

import java.util.List;
import java.sql.SQLException;

import am.aca.dbmigration.sql.tables.Table;
import am.aca.dbmigration.generators.GeneratorFactory;
import am.aca.dbmigration.converters.ConverterFactory;
import am.aca.dbmigration.analyzers.DDLAnalyzerFactory;
import am.aca.dbmigration.dataMigrator.DataMigratorFactory;
import am.aca.dbmigration.sql.generatedSQLs.UnsupportedFeatures;

public class SchemaAnalyzer {


    public static List<? extends Table> getSchema(String urlFrom, String usernameFrom, String passwordFrom) throws SQLException {
        MigrationData.schemaFrom = DDLAnalyzerFactory.
                getAnalyzer(urlFrom, usernameFrom, passwordFrom)
                .getSchema();

        return MigrationData.schemaFrom.getTables();

    }

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

    public static List<String> generateSqls() throws SQLException {

        MigrationData.schemaTo = ConverterFactory.getConverter(MigrationData.urlFrom, MigrationData.urlTo).convert(MigrationData.schemaFrom);
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
