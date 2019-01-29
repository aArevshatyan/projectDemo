package am.aca.generators;

import java.sql.*;
import java.util.*;

import am.aca.components.Schema;
import am.aca.components.tables.PostgreSQLTable;
import am.aca.components.constraints.PostgreSQLConstraint;

public class PostgreSQLGenerator implements Generator<PostgreSQLTable> {

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
    public void generateSQLOf(Schema<PostgreSQLTable> schema) {
        schema
                .getTables()
                .stream()
                .filter(PostgreSQLTable::isEnabled)
                .forEach(table -> {
                    StringBuilder sql = new StringBuilder();
                    sql
                            .append("CREATE TABLE IF NOT EXISTS ")
                            .append(table.getName())
                            .append(" (");

                    table
                            .getColumns()
                            .stream()
                            .forEach(column -> sql
                                    .append(column.getName())
                                    .append(" ")
                                    .append(column.getType())
                                    .append(" ")
                                    .append(column.getDefaultValue() != null ? "DEFAULT " + column.getDefaultValue() : "")
                                    .append(" ")
                                    .append(column.getIsNullable())
                                    .append(","));

                    sql.setLength(sql.length() - 1);
                    sql.append(");\n");

                    GeneratedSQLs.add(sql.toString());
                    sql.setLength(0);
                });

        schema
                .getTables()
                .stream()
                .filter(table -> table.getConstraintByPrimaryKey().size() != 0)
                .filter(PostgreSQLTable::isEnabled)
                .forEach(table -> {
                    StringBuilder sql = new StringBuilder();
                    sql
                            .append(" ALTER TABLE ")
                            .append(table.getName())
                            .append(" ADD PRIMARY KEY(");
                    for (PostgreSQLConstraint constraint : table.getConstraintByPrimaryKey()) {
                        sql.append(constraint.getColumn()).append(",");
                    }
                    sql.setLength(sql.length() - 1);
                    sql.append(");\n");

                    GeneratedSQLs.add(sql.toString());
                    sql.setLength(0);
                });
        schema
                .getTables()
                .stream()
                .filter(table -> table.getConstraintByPrimaryKey().size() != 0)
                .filter(PostgreSQLTable::isEnabled)
                .forEach(table -> {

                    table
                            .getConstraintByForeignKey()
                            .forEach(constraint -> {
                                boolean enabledReferencedTable = false;
                                for (PostgreSQLTable sqlTable : schema.getTables()) {
                                    if (sqlTable.getName().equalsIgnoreCase(constraint.getReferencedTable())
                                            && sqlTable.isEnabled()) {
                                        enabledReferencedTable = true;
                                        break;
                                    }
                                }
                                if (enabledReferencedTable) {
                                    StringBuilder sql = new StringBuilder();
                                    sql.append(" ALTER TABLE " + constraint.getTable() + " ADD CONSTRAINT ")
                                            .append(constraint.getName())
                                            .append(" FOREIGN KEY")
                                            .append("(")
                                            .append(constraint.getColumn())
                                            .append(")")
                                            .append(" REFERENCES ")
                                            .append(constraint.getReferencedTable())
                                            .append("(")
                                            .append(constraint.getReferencedColumn())
                                            .append(");\n");

                                    GeneratedSQLs.add(sql.toString());
                                    sql.setLength(0);

                                } else {
                                    UnsupportedFeatures.add("Can't create " + constraint.getTable() + " FK CONSTRAINT from " + constraint.getColumn() + " to " +
                                            constraint.getReferencedColumn() + " column because " + constraint.getReferencedTable() + " doesn't exists");
                                }
                            });
                });

        System.out.println(GeneratedSQLs.generatedSQLs);
        System.out.println("-----------");
        System.out.println(UnsupportedFeatures.unsupportedFeatures);


        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/test2",
                    "postgres",
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
