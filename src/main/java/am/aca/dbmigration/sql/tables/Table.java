package am.aca.dbmigration.sql.tables;

import java.util.List;

public interface Table {
    String getName();
    List getColumns();
    boolean isEnabled();
    void setEnabled(boolean enabled);
}