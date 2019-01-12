package am.aca.analyzers;

import am.aca.components.Schema;

import java.sql.SQLException;

public interface DDLAnalyzer {

    Schema getSchema() throws SQLException;

}
