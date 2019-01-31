package am.aca.backWork.analyzers;

import java.sql.*;

import am.aca.backWork.components.Schema;
import am.aca.backWork.components.utils.Nullable;
import am.aca.backWork.components.tables.PostgreSQLTable;
import am.aca.backWork.components.columns.PostgreSQLColumn;
import am.aca.backWork.components.constraints.PostgreSQLConstraint;

public class PostgreSQLDDLAnalyzer implements DDLAnalyzer<PostgreSQLTable> {

    private Connection connection;
    private String url;
    private String username;
    private String password;

    public PostgreSQLDDLAnalyzer(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Schema<PostgreSQLTable> getSchema() throws SQLException {

        this.connection = DriverManager.getConnection(
                url,
                username,
                password
        );

        Schema<PostgreSQLTable> schema = new Schema<>();
        getTablesFromDB(schema);

        return schema;

    }

    private void getTablesFromDB(Schema<PostgreSQLTable> schema) throws SQLException {

        String showTablesSql =
                "SELECT TABLE_NAME, TABLE_TYPE " +
                        "FROM INFORMATION_SCHEMA.TABLES " +
                        "WHERE TABLE_SCHEMA = 'public'";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {
            PostgreSQLTable table = new PostgreSQLTable(resultSet.getString(1), resultSet.getString(2));
            getColumnsFromDb(table);
            getConstraintsFromDb(table);
            schema.addTable(table);
        }

    }

    private void getColumnsFromDb(PostgreSQLTable table) throws SQLException {

        PreparedStatement showColumnsStatement = connection.prepareStatement(
                "SELECT COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, " +
                        "IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, " +
                        "CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE  " +
                        " FROM INFORMATION_SCHEMA.COLUMNS " +
                        " WHERE TABLE_SCHEMA = 'public' " +
                        " AND TABLE_NAME   = ?;");
        showColumnsStatement.setString(1, table.getName());
        ResultSet resultSet = showColumnsStatement.executeQuery();
        while (resultSet.next()) {
            table.addColumn(
                    new PostgreSQLColumn(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            Nullable.valueOf(resultSet.getString(4)),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9)
                    )
            );
        }
    }

    private void getConstraintsFromDb(PostgreSQLTable table) throws SQLException {
        PreparedStatement showFkeysStatement = connection.prepareStatement(
                "SELECT  TC.CONSTRAINT_NAME, TC.CONSTRAINT_TYPE, TC.TABLE_NAME, KCU.COLUMN_NAME,  CCU.TABLE_NAME, CCU.COLUMN_NAME " +
                        " FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC " +
                        " JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU" +
                        " ON TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME" +
                        " AND TC.TABLE_SCHEMA = KCU.TABLE_SCHEMA" +
                        " JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE AS CCU" +
                        " ON CCU.CONSTRAINT_NAME = TC.CONSTRAINT_NAME" +
                        " AND CCU.TABLE_SCHEMA = TC.TABLE_SCHEMA" +
                        " WHERE TC.TABLE_NAME = ? ;");

        showFkeysStatement.setString(1, table.getName());
        ResultSet resultSet = showFkeysStatement.executeQuery();
        while (resultSet.next()) {
            table.addConstraint(
                    new PostgreSQLConstraint(
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
}