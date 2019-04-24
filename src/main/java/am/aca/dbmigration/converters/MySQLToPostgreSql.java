package am.aca.dbmigration.converters;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.columns.MySQLColumn;
import am.aca.dbmigration.sql.columns.PostgreSQLColumn;
import am.aca.dbmigration.sql.constraints.MySQLConstraint;
import am.aca.dbmigration.sql.constraints.PostgreSQLConstraint;
import am.aca.dbmigration.sql.tables.MySQLTable;
import am.aca.dbmigration.sql.tables.PostgreSQLTable;
import am.aca.dbmigration.sql.utils.Type;

/**
 * Converter from MySQL to PostgreSQL
 */
public class MySQLToPostgreSql implements Converter<MySQLTable, PostgreSQLTable> {

    /**
     * This function takes source type schema then by using foreach loop
     * gets all information from it and set in destination type schema
     *
     * @param schemaFrom is MySQL database type schema
     * @return PostgreSQL database type schema
     * @see Converter#convert(Schema)
     */
    @Override
    public Schema<PostgreSQLTable> convert(Schema<MySQLTable> schemaFrom) {
        Schema<PostgreSQLTable> schemaTo = new Schema<>();

        for (MySQLTable tablefrom : schemaFrom.getTables()) {
            PostgreSQLTable tableTo = new PostgreSQLTable(tablefrom.getName(), tablefrom.getType());
            tableTo.setEnabled(tablefrom.isEnabled());
            for (MySQLColumn columnFrom : tablefrom.getColumns()) {
                PostgreSQLColumn columnTo = new PostgreSQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), Type.getDataType("mysql", "postgresql",
                        columnFrom.getDataType().toUpperCase()), columnFrom.getCharacterMaximumLength(),
                        columnFrom.getCharacterOctetLength(), columnFrom.getNumericPrecision(),
                        columnFrom.getNumericScale()
                );
                tableTo.addColumn(columnTo);
            }
            for (MySQLConstraint constraintFrom : tablefrom.getConstraints()) {
                PostgreSQLConstraint constraintTo = new PostgreSQLConstraint(
                        constraintFrom.getName(), constraintFrom.getType(),
                        constraintFrom.getTable(), constraintFrom.getColumn(),
                        constraintFrom.getReferencedTable(), constraintFrom.getReferencedColumn()
                );
                tableTo.addConstraint(constraintTo);
            }
            schemaTo.addTable(tableTo);
        }
        return schemaTo;
    }
}