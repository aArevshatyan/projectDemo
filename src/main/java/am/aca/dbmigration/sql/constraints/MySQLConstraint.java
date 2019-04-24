package am.aca.dbmigration.sql.constraints;

import java.util.Objects;

/**
 * Holds information about MySQL constraints
 */
public class MySQLConstraint {

    private String name;
    private String type;
    private String table;
    private String column;
    private String referencedTable;
    private String referencedColumn;

    public MySQLConstraint(String name, String type, String table, String column, String referencedTable, String referencedColumn) {
        this.name = name;
        this.type = type;
        this.table = table;
        this.column = column;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MySQLConstraint)) return false;
        MySQLConstraint that = (MySQLConstraint) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getTable(), that.getTable()) &&
                Objects.equals(getColumn(), that.getColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getTable(), getColumn());
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }

    public void setReferencedColumn(String referencedColumn) {
        this.referencedColumn = referencedColumn;
    }

    @Override
    public String toString() {
        return "MySQLConstraint{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", referencedTable='" + referencedTable + '\'' +
                ", referencedColumn='" + referencedColumn + '\'' +
                '}';
    }
}