package am.aca.generators;

import java.sql.*;
import java.util.*;

import am.aca.components.Schema;
import am.aca.components.tables.MySQLTable;

public class MySQLGenarator implements Generator<MySQLTable> {

    private static class UnsupportedFeatures {
        private static List<String> unsupportedFeatures = new ArrayList<>();

        public static void add(String s) {
            unsupportedFeatures.add(s);
        }
    }

    private static class GeneratedSQLs {
        private static List<String> generatedSQLs = new ArrayList<>();

        public static void add(String s) {
            generatedSQLs.add(s);
        }
    }

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

                    GeneratedSQLs.add(sql.toString());
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

                    GeneratedSQLs.add(sql.toString());
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

                                            GeneratedSQLs.add(sql.toString());
                                            sql.setLength(0);
                                        } else {
                                            UnsupportedFeatures.add("Can't create " + constraint.getTable() +
                                                    " FK CONSTRAINT from " + constraint.getColumn() + " to " +
                                                    constraint.getReferencedColumn() + " column because " +
                                                    constraint.getReferencedTable() + " table doesn't exists");

                                        }
                                    });
                });

        System.out.println(UnsupportedFeatures.unsupportedFeatures);
        System.out.println("--------------");
        System.out.println(GeneratedSQLs.generatedSQLs);

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test2",
                    "root",
                    "root"
            );

            for (String s : GeneratedSQLs.generatedSQLs) {
                PreparedStatement preparedStatement = connection.prepareStatement(s);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
