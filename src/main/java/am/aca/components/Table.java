package am.aca.components;

import java.util.List;
import java.util.ArrayList;

public class Table {

    private String name;
    private List<Column> columns;
    private List<Constraint> constraints;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

}
