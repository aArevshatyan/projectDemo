package am.aca.dbmigration.sql.tables;

import java.util.List;

public interface Table {
    String getName();

    /**
     * @return table's list of columns
     */
    List getColumns();

    /**
     * @return if the table is selected by user for migration
     */
    boolean isEnabled();

    /**
     * @param enabled user's choice about table migration
     */
    void setEnabled(boolean enabled);
}