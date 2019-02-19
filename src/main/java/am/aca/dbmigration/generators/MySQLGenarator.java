package am.aca.dbmigration.generators;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.generatedSQLs.*;
import am.aca.dbmigration.sql.tables.MySQLTable;

public class MySQLGenarator implements Generator<MySQLTable> {

    @Override
    public void generateSQLOf(Schema<MySQLTable> schema) {


        StringBuilder sql = new StringBuilder();

        schema
                .getTables()
                .stream()
                .filter(MySQLTable::isEnabled)
                .forEach(table -> {
                    sql.append("CREATE TABLE IF NOT EXISTS ").append(table.getName()).append("( \n ");
                    table
                            .getColumns()
                            .forEach(column ->
                                    sql
                                            .append(column.getName())
                                            .append(" ")
                                            .append(column.getType())
                                            .append(" ")
                                            .append(column.getIsNullable().toString())
                                            .append((column.getDefaultValue() != null &&
                                                    !"CURRENT_DATE".equalsIgnoreCase(column.getDefaultValue()))
                                                    ? " DEFAULT " + column.getDefaultValue() + " "
                                                    : " ")
                                            .append(",  ")
                            );
                    sql.replace(sql.toString().lastIndexOf(","), sql.toString().lastIndexOf(",") + 1, "");
                    sql.append(");\n\n\n");

                    GeneratedCreateSQLs.add(sql.toString());
                    sql.setLength(0);
                });

        schema
                .getTables()
                .stream()
                .filter(MySQLTable::isEnabled)
                .filter(table -> table.getConstraintByPrimaryKey().size() != 0)
                .forEach(table -> {
                    sql
                            .append("ALTER TABLE ")
                            .append(table.getName())
                            .append(" ADD PRIMARY KEY(");
                    table
                            .getConstraintByPrimaryKey()
                            .forEach(constraint -> {
                                sql
                                        .append(constraint.getColumn())
                                        .append(",");
                            });
                    sql.replace(sql.toString().lastIndexOf(","), sql.toString().lastIndexOf(",") + 1, "");
                    sql.append(");\n\n\n");

                    GeneratedPrimarySQLs.add(sql.toString());
                    sql.setLength(0);
                });

        schema
                .getTables()
                .stream()
                .filter(MySQLTable::isEnabled)
                .filter(table -> table.getConstraintByForeignKey().size() != 0)
                .forEach(table -> {
                    table
                            .getConstraintByForeignKey()
                            .forEach(
                                    constraint -> {
                                        boolean enabledReferencedTable = false;
                                        for (MySQLTable sqlTable : schema.getTables()) {
                                            if (sqlTable.getName().equalsIgnoreCase(constraint.getReferencedTable())
                                                    && sqlTable.isEnabled()) {
                                                enabledReferencedTable = true;
                                                break;
                                            }
                                        }
                                        if (enabledReferencedTable) {
                                            sql
                                                    .append("ALTER TABLE ")
                                                    .append(constraint.getTable())
                                                    .append(" ADD FOREIGN KEY ")
                                                    .append(constraint.getName())
                                                    .append(" (")
                                                    .append(constraint.getColumn())
                                                    .append(")  REFERENCES ")
                                                    .append(constraint.getReferencedTable())
                                                    .append(" (")
                                                    .append(constraint.getReferencedColumn())
                                                    .append("); \n \n");

                                            GeneratedForeignSQls.add(sql.toString());
                                            sql.setLength(0);
                                        } else {
                                            UnsupportedFeatures.add("Can't create " + constraint.getTable() +
                                                    " FK CONSTRAINT from " + constraint.getColumn() + " to " +
                                                    constraint.getReferencedColumn() + " column because " +
                                                    constraint.getReferencedTable() + " table doesn't exists");

                                        }
                                    });
                });
        UnsupportedFeatures.add("Indexes aren't supported");


    }
}
