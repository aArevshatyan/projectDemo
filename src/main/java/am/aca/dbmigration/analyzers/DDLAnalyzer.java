package am.aca.dbmigration.analyzers;

import java.sql.SQLException;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.tables.Table;

public interface DDLAnalyzer<T extends Table> {

    Schema<T> getSchema() throws SQLException;

}
