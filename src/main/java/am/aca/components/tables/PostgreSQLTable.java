package am.aca.components.tables;

import java.util.List;
import java.util.ArrayList;

import am.aca.components.columns.PostgreSQLColumn;
import am.aca.components.constraints.PostgreSQLConstraint;

public class PostgreSQLTable {

    private String name;
    private String type;

    private List<PostgreSQLColumn> columns;
    private List<PostgreSQLConstraint> constraints;

    public PostgreSQLTable(String name, String type){
        this.name = name;
        this.type = type;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public void addColumn(PostgreSQLColumn column){
        this.columns.add(column);
    }

    public void addConstraint (PostgreSQLConstraint constraint){
        this.constraints.add(constraint);
    }

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
        return "PostgreSQLTable{" +
                "name='" + name + '\'' +
                '}';
    }
}
