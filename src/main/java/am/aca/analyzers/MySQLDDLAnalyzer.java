package am.aca.analyzers;

import am.aca.components.Schema;

import java.sql.*;

public class MySQLDDLAnalyzer implements DDLAnalyzer{

    @Override
    public Schema getSchema() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test2",
                "root",
                "root"
        );


        String showTablesSql = "SHOW TABLES;";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {

            String tableName = resultSet.getString(1);
            System.out.println(tableName);
            //chi stacvum prepStat setStringy chakertova avelacnum tableNamey
            String showColumnsSql = "SHOW COLUMNS FROM " + tableName + ";";
            Statement showColumnsStatement = connection.createStatement();
            ResultSet resultSet1 = showColumnsStatement.executeQuery(showColumnsSql);
            while (resultSet1.next()) {
                System.out.print("\t\t" + resultSet1.getString(1));
                System.out.print("\t\t" + resultSet1.getString(2));
                System.out.print("\t\t" + resultSet1.getString(3));
                System.out.print("\t\t" + resultSet1.getString(4));
                System.out.print("\t\t" + resultSet1.getString(5));
                System.out.println("\t\t" + resultSet1.getString(6));

            }
            PreparedStatement showFkeysStatement = connection.prepareStatement(
                    "SELECT  COLUMN_NAME,  CONSTRAINT_NAME,  REFERENCED_TABLE_NAME,  REFERENCED_COLUMN_NAME " +
                            "  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                            "  WHERE TABLE_NAME = ? " +
                            "AND REFERENCED_TABLE_NAME IS NOT NULL AND REFERENCED_COLUMN_NAME IS NOT NULL;"
            );
            showFkeysStatement.setString(1, tableName);
            ResultSet resultSet2 = showFkeysStatement.executeQuery();
            boolean b = true;
            while (resultSet2.next()) {
                if (b) {
                    System.out.println("FOREIGN KEYs for " + tableName);
                    b = false;
                }
                System.out.print("\t\t" + resultSet2.getString(1));
                System.out.print("\t\t" + resultSet2.getString(2));
                System.out.print("\t\t" + resultSet2.getString(3));
                System.out.println("\t\t" + resultSet2.getString(4));
            }
            System.out.println();
        }
        return null;
    }
}
