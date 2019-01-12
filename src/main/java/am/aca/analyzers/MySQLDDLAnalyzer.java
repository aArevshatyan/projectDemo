package am.aca.analyzers;

import java.sql.*;
import java.util.List;

import am.aca.components.*;

public class MySQLDDLAnalyzer implements DDLAnalyzer {

    private Connection connection;

    @Override
    public Schema getSchemaOf(String url) throws SQLException {
        String user = "root";
        String password = "root";
        this.connection = DriverManager.getConnection(
                url,
                user,
                password
        );

        Schema schema = new Schema();
        getTablesFromDB(schema.getTables());

        return schema;
    }

    private void getTablesFromDB(List<Table> tables) throws SQLException {

        String showTablesSql = "SHOW TABLES;";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {
            Table table = new Table(resultSet.getString(1));
            getColumnsFromDb(table.getName(), table.getColumns());
            getConstraintsFromDb(table.getName(), table.getConstraints());
            tables.add(table);
        }

    }

    private void getColumnsFromDb(String tableName, List<Column> columns) throws SQLException {
        String showColumnsSql = "SHOW COLUMNS FROM " + tableName + ";";
        Statement showColumnsStatement = connection.createStatement();
        ResultSet resultSet = showColumnsStatement.executeQuery(showColumnsSql);
        while (resultSet.next()) {

            columns.add(
                    new Column(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
    }

    private void getConstraintsFromDb(String tableName, List<Constraint> constraints) throws SQLException {
        PreparedStatement showFkeysStatement = connection.prepareStatement(
                "SELECT  TABLE_NAME, COLUMN_NAME,  CONSTRAINT_NAME,  REFERENCED_TABLE_NAME,  REFERENCED_COLUMN_NAME " +
                        "  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                        "  WHERE TABLE_NAME = ? " +
                        "AND REFERENCED_TABLE_NAME IS NOT NULL AND REFERENCED_COLUMN_NAME IS NOT NULL;"
        );
        showFkeysStatement.setString(1, tableName);
        ResultSet resultSet = showFkeysStatement.executeQuery();
        while (resultSet.next()) {
            constraints.add(
                    new Constraint(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
    }
}




