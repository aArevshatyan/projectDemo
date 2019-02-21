package am.aca.dbmigration.sql.tables;

import am.aca.dbmigration.sql.columns.PostgreSQLColumn;
import am.aca.dbmigration.sql.constraints.PostgreSQLConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PostgreSQL type table
 */
public class PostgreSQLTable implements Table {

    private String name;
    private String type;
    private boolean enabled;

    private List<PostgreSQLColumn> columns;
    private List<PostgreSQLConstraint> constraints;

    public PostgreSQLTable(String name, String type) {
        this.name = name;
        this.type = type;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    /**
     * @return list of PostgreSQL type primary key constraints
     */
    public List<PostgreSQLConstraint> getConstraintByPrimaryKey() {
        List<PostgreSQLConstraint> constraintsByPK = new ArrayList<>();
        for (PostgreSQLConstraint mySQLConstraint : constraints) {
            if ("PRIMARY KEY".equals(mySQLConstraint.getType())) {
                constraintsByPK.add(mySQLConstraint);
            }
        }

        return constraintsByPK.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @return list of PostgreSQL type foreign key constraints
     */
    public List<PostgreSQLConstraint> getConstraintByForeignKey() {
        List<PostgreSQLConstraint> constraintsByFK = new ArrayList<>();
        for (PostgreSQLConstraint mySQLConstraint : constraints) {
            if ("FOREIGN KEY".equals(mySQLConstraint.getType())) {
                constraintsByFK.add(mySQLConstraint);
            }
        }
        return constraintsByFK;
    }

    public void addColumn(PostgreSQLColumn column) {
        this.columns.add(column);
    }

    public void addConstraint(PostgreSQLConstraint constraint) {
        this.constraints.add(constraint);
    }

    /**
     * @return list of PostgreSql type columns
     * @see Table#getColumns()
     */
    @Override
    public List<PostgreSQLColumn> getColumns() {
        return new ArrayList<>(columns);
    }

    public void setPostgreSQLColumns(List<PostgreSQLColumn> postgreSQLColumns) {
        this.columns = postgreSQLColumns;
    }

    public List<PostgreSQLConstraint> getConstraints() {
        return new ArrayList<>(constraints);
    }

    public void setPostgreSQLConstraints(List<PostgreSQLConstraint> postgreSQLConstraints) {
        this.constraints = postgreSQLConstraints;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return if the table is selected by user for migration
     * @see Table#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled user's choice about table migration
     * @see Table#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "PostgreSQLTable{" +
                "name='" + name + '\'' +
                '}';
    }
}
