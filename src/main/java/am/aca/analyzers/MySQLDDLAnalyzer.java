package am.aca.analyzers;

import java.sql.*;
import java.util.List;

import am.aca.components.*;

public class MySQLDDLAnalyzer implements DDLAnalyzer {

    private Connection connection;

    @Override
    public Schema<MySQLTable> getSchemaOf(String url) throws SQLException {
        String user = "root";
        String password = "root";
        this.connection = DriverManager.getConnection(
                url,
                user,
                password
        );

        Schema<MySQLTable> schema = new Schema<>();
        getTablesFromDB(schema.getTables());

        return schema;
    }

    private void getTablesFromDB(List<MySQLTable> tables) throws SQLException {

        String showTablesSql =
                " SELECT TABLE_NAME " +
                " FROM INFORMATION_SCHEMA.TABLES" +
                " WHERE TABLE_SCHEMA = 'test2'";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {
            MySQLTable table = new MySQLTable(resultSet.getString(1));
            getColumnsFromDb(table.getTableName(), table.getColumns());
            getConstraintsFromDb(table.getTableName(), table.getConstraints());
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
                        " FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                        " WHERE TABLE_NAME = ? " +
                        " AND REFERENCED_TABLE_NAME IS NOT NULL AND REFERENCED_COLUMN_NAME IS NOT NULL;"
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




