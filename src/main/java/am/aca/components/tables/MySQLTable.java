package am.aca.components.tables;

import java.util.List;
import java.util.ArrayList;

import am.aca.components.columns.MySQLColumn;
import am.aca.components.constraints.MySQLConstraint;

public class MySQLTable {

    private String name;
    private String type;

    private List<MySQLColumn> columns;
    private List<MySQLConstraint> constraints;


    public MySQLTable(String name, String type){
        this.name = name;
        this.type = type;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }


    public void addColumn(MySQLColumn column){
        this.columns.add(column);
    }

    public void addConstraint (MySQLConstraint constraint){
        this.constraints.add(constraint);
}

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
}
