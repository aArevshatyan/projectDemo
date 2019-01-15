package am.aca.analyzers;

import java.sql.SQLException;

import am.aca.components.Schema;

public interface DDLAnalyzer {

    Schema getSchemaOf(String url) throws SQLException;

}
