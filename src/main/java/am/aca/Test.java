package am.aca;

import java.sql.*;

public class Test {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "root"
        );


        String showTablesSql = "show tables;";
        Statement showTablesStatement = connection.createStatement();
        ResultSet resultSet = showTablesStatement.executeQuery(showTablesSql);

        while (resultSet.next()) {


            System.out.println(resultSet.getString(1));
            String showColumnsSql = "SHOW COLUMNS FROM " + resultSet.getString(1) + ";";
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
            String showFkeysSql =
                    "SELECT  COLUMN_NAME,  CONSTRAINT_NAME,  REFERENCED_TABLE_NAME,  REFERENCED_COLUMN_NAME " +
                            "  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                            "  WHERE TABLE_NAME = \'" + resultSet.getString(1) + "\'" +
                            "and  REFERENCED_TABLE_NAME is not null and  REFERENCED_COLUMN_NAME is not null;";
            Statement showFkeysStatement = connection.createStatement();
            ResultSet resultSet2 = showFkeysStatement.executeQuery(showFkeysSql);
            boolean b = true;
            while (resultSet2.next()) {
                if (b) {
                    System.out.println("FOREIGN KEYs for " + resultSet.getString(1));
                    b = false;
                }
                System.out.print("\t\t" + resultSet2.getString(1));
                System.out.print("\t\t" + resultSet2.getString(2));
                System.out.print("\t\t" + resultSet2.getString(3));
                System.out.println("\t\t" + resultSet2.getString(4));
            }
            System.out.println();
        }
    }


}
