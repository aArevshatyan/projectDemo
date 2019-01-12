package am.aca.components;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String Name;
    private List<Column> columns;
    private List<Constraint> constraints;

    public Table(String name) {
        Name = name;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }
}
