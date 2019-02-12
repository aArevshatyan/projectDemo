package am.aca.dbmigration.sql.tables;

import java.util.List;

public interface Table {
    boolean isEnabled();
    List getColumns();
    String getName();
}