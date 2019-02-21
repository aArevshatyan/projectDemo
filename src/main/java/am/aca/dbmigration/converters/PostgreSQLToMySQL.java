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
 * Converter from PostgreSQL to MySQL
 */
public class PostgreSQLToMySQL implements Converter<PostgreSQLTable, MySQLTable> {

    /**
     * This function takes source type schema then by using foreach loop
     * gets all information from it and set in destination type schema
     *
     * @param schemaFrom is  PostgreSQL  database type schema
     * @return MySQL database type schema
     * @see Converter#convert(Schema)
     */
    @Override
    public Schema<MySQLTable> convert(Schema<PostgreSQLTable> schemaFrom) {
        Schema<MySQLTable> schemaTo = new Schema<>();

        for (PostgreSQLTable tablefrom : schemaFrom.getTables()) {
            MySQLTable tableTo = new MySQLTable(tablefrom.getName(), tablefrom.getType());
            tableTo.setEnabled(tablefrom.isEnabled());
            for (PostgreSQLColumn columnFrom : tablefrom.getColumns()) {

                MySQLColumn columnTo = new MySQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), Type.getDataType("postgresql", "mysql", columnFrom.getDataType().toUpperCase()), columnFrom.getCharacterMaximumLength(),
                        columnFrom.getCharacterOctetLength(), columnFrom.getNumericPrecision(), columnFrom.getNumericScale()
                );
                tableTo.addColumn(columnTo);
            }
            for (PostgreSQLConstraint constraintFrom : tablefrom.getConstraints()) {
                MySQLConstraint constraintTo = new MySQLConstraint(
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
