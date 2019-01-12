package am.aca.components;

public class Constraint {

    private String table;
    private String column;
    private String constraint;
    private String referencedTable;
    private String referencedColumn;

    public Constraint (String table, String column, String constraint, String referencedTable, String referencedColumn) {
        this.table = table;
        this.column = column;
        this.constraint = constraint;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getConstraint() {
        return constraint;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }
}
