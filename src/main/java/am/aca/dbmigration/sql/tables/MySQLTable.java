package am.aca.dbmigration.sql.tables;

import am.aca.dbmigration.sql.columns.MySQLColumn;
import am.aca.dbmigration.sql.constraints.MySQLConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MySQL type table
 */
public class MySQLTable implements Table {

    private String name;
    private String type;
    private boolean enabled;

    private List<MySQLColumn> columns;
    private List<MySQLConstraint> constraints;

    public MySQLTable(String name, String type) {
        this.name = name;
        this.type = type;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    /**
     * @return list of MySQL type primary key constraints
     */
    public List<MySQLConstraint> getConstraintByPrimaryKey() {
        List<MySQLConstraint> constraintsByPK = new ArrayList<>();
        for (MySQLConstraint mySQLConstraint : constraints) {
            if ("PRIMARY KEY".equals(mySQLConstraint.getType())) {
                constraintsByPK.add(mySQLConstraint);
            }
        }
        return constraintsByPK.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @return list of MySQL type foreign key constraints
     */
    public List<MySQLConstraint> getConstraintByForeignKey() {
        List<MySQLConstraint> constraintsByFK = new ArrayList<>();
        for (MySQLConstraint mySQLConstraint : constraints) {
            if ("FOREIGN KEY".equals(mySQLConstraint.getType())) {
                constraintsByFK.add(mySQLConstraint);
            }
        }
        return constraintsByFK;
    }

    public void addColumn(MySQLColumn column) {
        this.columns.add(column);
    }

    public void addConstraint(MySQLConstraint constraint) {
        this.constraints.add(constraint);
    }

    /**
     * @return list of MySql type columns
     * @see Table#getColumns()
     */
    @Override
    public List<MySQLColumn> getColumns() {
        return new ArrayList<>(columns);
    }

    public void setColumns(List<MySQLColumn> columns) {
        this.columns = columns;
    }

    public List<MySQLConstraint> getConstraints() {
        return new ArrayList<>(constraints);
    }

    public void setConstraints(List<MySQLConstraint> constraints) {
        this.constraints = constraints;
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
        return "MySQLTable{" +
                "name='" + name + '\'' +
                '}';
    }
}