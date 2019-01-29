package am.aca.analyzers;

import java.sql.SQLException;

import am.aca.components.Schema;

public interface DDLAnalyzer<R> {

    Schema<R> getSchemaOf(String url) throws SQLException;

}
