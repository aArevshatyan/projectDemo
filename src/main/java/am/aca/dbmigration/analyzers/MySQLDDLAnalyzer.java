package am.aca.dbmigration.analyzers;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.columns.MySQLColumn;
import am.aca.dbmigration.sql.constraints.MySQLConstraint;
import am.aca.dbmigration.sql.tables.MySQLTable;
import am.aca.dbmigration.sql.utils.JdbcUrlHelper;
import am.aca.dbmigration.sql.utils.Nullable;

import java.sql.*;

/**
 * MySQL type Analyzer
 */
public class MySQLDDLAnalyzer implements DDLAnalyzer<MySQLTable> {
    private Connection connection;
    private String url;
    private String username;
    private String password;
    private String dbName;

    public MySQLDDLAnalyzer(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.dbName = JdbcUrlHelper.getDbName(url);
    }

    /**
     * @return MySql type schema
     * @throws SQLException
     * @see DDLAnalyzer#getSchema()
     */
    @Override
    public Schema<MySQLTable> getSchema() throws SQLException {
        this.connection = DriverManager.getConnection(
                url,
                username,
                password
        );
        Schema<MySQLTable> schema = new Schema<>();
        getTablesFromDB(schema);
        return schema;
    }

    /**
     * By source database connection it selects the tables from
     * information schema in database and creates Table object
     * that is added to schema
     *
     * @param schema in which are added tables
     * @throws SQLException
     */
    private void getTablesFromDB(Schema<MySQLTable> schema) throws SQLException {
        PreparedStatement showTablesStatement = connection.prepareStatement(
                " SELECT TABLE_NAME, TABLE_TYPE " +
                        " FROM INFORMATION_SCHEMA.TABLES" +
                        " WHERE TABLE_SCHEMA = ?;");
        showTablesStatement.setString(1, dbName);
        ResultSet resultSet = showTablesStatement.executeQuery();
        while (resultSet.next()) {
            MySQLTable table = new MySQLTable(resultSet.getString(1), resultSet.getString(2));
            getColumnsFromDb(table);
            getConstraintsFromDb(table);
            schema.addTable(table);
        }
    }

    /**
     * By source database connection it selects the passed tables's
     * columns from information schema in database and creates Column object
     * that is added to table
     *
     * @param table in which are added columns
     * @throws SQLException
     */
    private void getColumnsFromDb(MySQLTable table) throws SQLException {
        PreparedStatement showColumnsStatement = connection.prepareStatement(
                "SELECT COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, " +
                        "IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, " +
                        "NUMERIC_PRECISION, NUMERIC_SCALE " +
                        "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ;");

        showColumnsStatement.setString(1, dbName);
        showColumnsStatement.setString(2, table.getName());
        ResultSet resultSet = showColumnsStatement.executeQuery();
        while (resultSet.next()) {

            table.addColumn(
                    new MySQLColumn(
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

    /**
     * By source database connection it selects the passed tables's
     * constraints from information schema in database and creates
     * Constraint object that is added to table
     *
     * @param table in which are added columns
     * @throws SQLException
     */
    private void getConstraintsFromDb(MySQLTable table) throws SQLException {
        PreparedStatement showFkeysStatement = connection.prepareStatement(
                "SELECT  K.CONSTRAINT_NAME, C.CONSTRAINT_TYPE, K.TABLE_NAME, K.COLUMN_NAME,  " +
                        " K.REFERENCED_TABLE_NAME,  K.REFERENCED_COLUMN_NAME " +
                        " FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS  K ," +
                        " INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS C" +
                        " WHERE  K.TABLE_NAME = C.TABLE_NAME AND K.CONSTRAINT_NAME = C.CONSTRAINT_NAME " +
                        "AND K.TABLE_NAME = ? AND K.TABLE_SCHEMA = ? and C.TABLE_SCHEMA = ?; ");
        showFkeysStatement.setString(1, table.getName());
        showFkeysStatement.setString(2, dbName);
        showFkeysStatement.setString(3, dbName);
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