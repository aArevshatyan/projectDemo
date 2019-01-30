package am.aca.analyzers;

import java.sql.SQLException;

import am.aca.components.Schema;

public interface DDLAnalyzer<R> {

    Schema<R> getSchema() throws SQLException;

}
