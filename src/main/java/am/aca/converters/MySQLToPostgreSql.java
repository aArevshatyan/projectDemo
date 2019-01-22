package am.aca.converters;

import am.aca.components.Schema;
import am.aca.components.tables.*;
import am.aca.components.columns.*;
import am.aca.components.constraints.*;

public class MySQLToPostgreSql implements Converter {
    @Override
    public Schema<PostgreSQLTable> convert(Schema schema) {
        Schema<MySQLTable> schemaFrom = schema;
        Schema<PostgreSQLTable> schemaTo = new Schema<>();

        //todo numeric typeri chapery voroshel
        for (MySQLTable tablefrom : schemaFrom.getTables()) {
            PostgreSQLTable tableTo = new PostgreSQLTable(tablefrom.getName(), tablefrom.getType());
            for (MySQLColumn columnFrom : tablefrom.getColumns()) {
                PostgreSQLColumn columnTo = new PostgreSQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), columnFrom.getDataType(), columnFrom.getCharacterMaximumLength(),
                        columnFrom.getCharacterOctetLength(), columnFrom.getNumericPrecision(), columnFrom.getNumericScale()
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
