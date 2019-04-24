package am.aca.dbmigration.dataMigrator;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedInsertSQLs;
import am.aca.dbmigration.sql.tables.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It's responsible for filling and generating of queries.
 *
 * @param <T> destination database type
 */
public class DataMigrator<T> {
    private String urlFrom;
    private String usernameFrom;
    private String passwordFrom;
    private List<List<String>> dataList = new ArrayList<>();

    public DataMigrator(String urlFrom, String usernameFrom, String passwordFrom) {
        this.urlFrom = urlFrom;
        this.usernameFrom = usernameFrom;
        this.passwordFrom = passwordFrom;
    }

    /**
     * Based on generated source schema it selects data from database
     * stores it in list then generate insert queries for destination database
     *
     * @param schemaFrom is generated schema of source database
     */
    public void generateMigrationSQL(Schema<Table> schemaFrom) {
        schemaFrom
                .getTables()
                .stream()
                .filter(Table::isEnabled)
                .forEach(table -> {
                            dataList = new ArrayList<>();
                            int columnCount = table.getColumns().size();
                            String sqlSelect = "SELECT * from " + table.getName();
                            try {
                                Connection connectionFrom = DriverManager.getConnection(
                                        urlFrom,
                                        usernameFrom,
                                        passwordFrom
                                );
                                PreparedStatement preparedStatement = connectionFrom.prepareStatement(sqlSelect);
                                preparedStatement.setFetchSize(50);
                                ResultSet resultSet = preparedStatement.executeQuery();
                                while (resultSet.next()) {
                                    List<String> values = new ArrayList<>();
                                    for (int i = 1; i <= columnCount; i++) {
                                        values.add(resultSet.getString(i));
                                    }
                                    dataList.add(values);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            dataList
                                    .forEach(
                                            list -> {
                                                StringBuilder sql = new StringBuilder();
                                                sql.append("INSERT INTO ");
                                                sql.append(table.getName());
                                                sql.append(" VALUES ( ");
                                                for (String aList : list) {
                                                    sql.append("\'");
                                                    sql.append(aList);
                                                    sql.append("\' , ");
                                                }
                                                sql.replace(sql.toString().lastIndexOf(","), sql.toString().lastIndexOf(",") + 1, "");
                                                sql.append(");");
                                                GeneratedInsertSQLs.add(sql.toString());
                                            }
                                    );
                        }
                );
    }
}