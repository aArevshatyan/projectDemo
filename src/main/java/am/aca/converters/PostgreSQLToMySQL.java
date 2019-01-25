package am.aca.converters;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.components.columns.*;
import am.aca.components.constraints.*;
import am.aca.components.utils.Type;

public class PostgreSQLToMySQL implements Converter<PostgreSQLTable, MySQLTable> {
    @Override
    public Schema<MySQLTable> convert(Schema<PostgreSQLTable> schemaFrom) {
        Schema<MySQLTable> schemaTo = new Schema<>();

        for (PostgreSQLTable tablefrom : schemaFrom.getTables()) {
            MySQLTable tableTo = new MySQLTable(tablefrom.getName(), tablefrom.getType());
            for (PostgreSQLColumn columnFrom : tablefrom.getColumns()) {
                MySQLColumn columnTo = new MySQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), Type.getDataType("postgresql","mysql",columnFrom.getDataType().toUpperCase()), columnFrom.getCharacterMaximumLength(),
                        columnFrom.getCharacterOctetLength(), columnFrom.getNumericPrecision(), columnFrom.getNumericScale(),
                        null, null
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
