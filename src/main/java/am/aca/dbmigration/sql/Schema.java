package am.aca.dbmigration.sql;


import java.util.ArrayList;
import java.util.List;

/**
 * It contains the whole information about database schema
 * especially information about all tables in it , its' relations ,
 * column information etc.
 *
 * @param <T> Schema type based on database type
 */
public class Schema<T> {

    private List<T> tables;

    public Schema() {
        this.tables = new ArrayList<>();
    }

    public List<T> getTables() {
        return new ArrayList<>(tables);
    }

    public void setTables(List<T> tables) {
        this.tables = tables;
    }

    public void addTable(T table) {
        this.tables.add(table);
    }
}
