package am.aca.backWork.converters;

import am.aca.backWork.components.Schema;
import am.aca.backWork.components.tables.*;
import am.aca.backWork.components.columns.*;
import am.aca.backWork.components.utils.Type;
import am.aca.backWork.components.constraints.*;

public class MySQLToPostgreSql implements Converter<MySQLTable, PostgreSQLTable> {
    @Override
    public Schema<PostgreSQLTable> convert(Schema<MySQLTable> schemaFrom) {
        Schema<PostgreSQLTable> schemaTo = new Schema<>();

        for (MySQLTable tablefrom : schemaFrom.getTables()) {
            PostgreSQLTable tableTo = new PostgreSQLTable(tablefrom.getName(), tablefrom.getType());
            tableTo.setEnabled(tablefrom.isEnabled());
            for (MySQLColumn columnFrom : tablefrom.getColumns()) {
                PostgreSQLColumn columnTo = new PostgreSQLColumn(
                        columnFrom.getName(), columnFrom.getOrdinalPosition(), columnFrom.getDefaultValue(),
                        columnFrom.getIsNullable(), Type.getDataType("mysql", "postgresql", columnFrom.getDataType().toUpperCase()), columnFrom.getCharacterMaximumLength(),
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
