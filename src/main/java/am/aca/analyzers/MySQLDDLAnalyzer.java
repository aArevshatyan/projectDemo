package am.aca.analyzers;

import java.sql.*;

import am.aca.components.Schema;
import am.aca.components.tables.MySQLTable;
import am.aca.components.columns.MySQLColumn;
import am.aca.components.constraints.MySQLConstraint;

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
        getTablesFromDB(schema);

        return schema;
    }

    private void getTablesFromDB(Schema<MySQLTable> schema) throws SQLException {

        //todo schemayi anuny poxancel helperov
        String showTablesSql =
                " SELECT TABLE_NAME, TABLE_TYPE " +
                        " FROM INFORMATION_SCHEMA.TABLES" +
                        " WHERE TABLE_SCHEMA = 'test2'";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {
            MySQLTable table = new MySQLTable(resultSet.getString(1), resultSet.getString(2));
            getColumnsFromDb(table);
            getConstraintsFromDb(table);
            schema.addTable(table);
        }

    }

    private void getColumnsFromDb(MySQLTable table) throws SQLException {

        PreparedStatement showColumnsStatement = connection.prepareStatement(
                "SELECT COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, " +
                        "IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, " +
                        "NUMERIC_PRECISION, NUMERIC_SCALE, COLUMN_TYPE, COLUMN_KEY " +
                        "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? ;");
        showColumnsStatement.setString(1, table.getName());
        ResultSet resultSet = showColumnsStatement.executeQuery();
        while (resultSet.next()) {

            table.addColumn(
                    new MySQLColumn(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9),
                            resultSet.getString(10),
                            resultSet.getString(11)
                    )
            );
        }
    }

    private void getConstraintsFromDb(MySQLTable table) throws SQLException {
        PreparedStatement showFkeysStatement = connection.prepareStatement(
                "SELECT  K.CONSTRAINT_NAME, C.CONSTRAINT_TYPE, K.TABLE_NAME, K.COLUMN_NAME,   K.REFERENCED_TABLE_NAME,  K.REFERENCED_COLUMN_NAME " +
                        " FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS  K , INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS C" +
                        " WHERE  K.TABLE_NAME = C.TABLE_NAME AND K.CONSTRAINT_NAME = C.CONSTRAINT_NAME AND K.TABLE_NAME = ?;"

        );
        showFkeysStatement.setString(1, table.getName());
        ResultSet resultSet = showFkeysStatement.executeQuery();
        while (resultSet.next()) {
            table.addConstraint(
                    new MySQLConstraint(
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




