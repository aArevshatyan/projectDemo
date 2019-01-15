package am.aca.analyzers;

import java.sql.*;
import java.util.List;

import am.aca.components.*;

public class PostgreSQLDDLAnalyzer implements DDLAnalyzer {

    private Connection connection;

    @Override
    public Schema<PostgreSQLTable> getSchemaOf(String url) throws SQLException {
        String user = "postgres";
        String password = "root";
        this.connection = DriverManager.getConnection(
                url,
                user,
                password
        );

        Schema<PostgreSQLTable> schema = new Schema<>();
        getTablesFromDB(schema.getTables());

        return schema;

    }

    private void getTablesFromDB(List<PostgreSQLTable> tables) throws SQLException {

        String showTablesSql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {
            PostgreSQLTable table = new PostgreSQLTable(resultSet.getString(1));
            getColumnsFromDb(table.getTableName(), table.getColumns());
            getConstraintsFromDb(table.getTableName(), table.getConstraints());
            tables.add(table);
        }

    }

    private void getColumnsFromDb(String tableName, List<Column> columns) throws SQLException {
        PreparedStatement showColumnsStatement = connection.prepareStatement(
                "SELECT column_name, data_type, is_nullable, is_identity, column_default, is_generated " +
                        " FROM information_schema.columns " +
                        " WHERE table_schema = 'public' " +
                        " AND table_name   = ?;");
        showColumnsStatement.setString(1, tableName);
        ResultSet resultSet = showColumnsStatement.executeQuery();
        while (resultSet.next()) {

            columns.add(
                    new Column(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6))
            );
        }
    }

    private void getConstraintsFromDb(String tableName, List<Constraint> constraints) throws SQLException {
        PreparedStatement showFkeysStatement = connection.prepareStatement(
                "SELECT  tc.table_name, kcu.column_name, tc.constraint_name, ccu.table_name, ccu.column_name " +
                        " FROM information_schema.table_constraints AS tc " +
                        " JOIN information_schema.key_column_usage AS kcu" +
                        " ON tc.constraint_name = kcu.constraint_name" +
                        " AND tc.table_schema = kcu.table_schema" +
                        " JOIN information_schema.constraint_column_usage AS ccu" +
                        " ON ccu.constraint_name = tc.constraint_name" +
                        " AND ccu.table_schema = tc.table_schema" +
                        " WHERE tc.constraint_type = 'FOREIGN KEY' AND  tc.table_name= ? ;");

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