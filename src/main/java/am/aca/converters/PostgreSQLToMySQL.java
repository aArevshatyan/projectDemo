package am.aca.converters;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.components.columns.*;
import am.aca.components.constraints.*;

public class PostgreSQLToMySQL implements Converter {
    @Override
    public Schema<MySQLTable> convert(Schema schema) {
        Schema<PostgreSQLTable> schemaFrom = schema;
        Schema<MySQLTable> schemaTo = new Schema<>();

        //todo numeric typeri chapery voroshel
        for (PostgreSQLTable tablefrom : schemaFrom.getTables()) {
            MySQLTable tableTo = new MySQLTable(tablefrom.getName(), tablefrom.getType());
            for (PostgreSQLColumn columnFrom : tablefrom.getColumns()) {
                MySQLColumn columnTo = new MySQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), columnFrom.getDataType(), columnFrom.getCharacterMaximumLength(),
                        columnFrom.getCharacterOctetLength(), columnFrom.getNumericPrecision(), columnFrom.getNumericScale(),
                        columnFrom.getDataType() + "(" + columnFrom.getNumericPrecision() + ")", null
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
