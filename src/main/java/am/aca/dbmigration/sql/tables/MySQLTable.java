package am.aca.dbmigration.sql.tables;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import am.aca.dbmigration.sql.columns.MySQLColumn;
import am.aca.dbmigration.sql.constraints.MySQLConstraint;

public class MySQLTable implements Table{

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


    public List<MySQLConstraint> getConstraintByPrimaryKey() {
        List<MySQLConstraint> constraintsByPK = new ArrayList<>();
        for (MySQLConstraint mySQLConstraint : constraints) {
            if ("PRIMARY KEY".equals(mySQLConstraint.getType())) {
                constraintsByPK.add(mySQLConstraint);
            }
        }

        return constraintsByPK.stream().distinct().collect(Collectors.toList());
    }

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

    @Override
    public String toString() {
        return "MySQLTable{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
