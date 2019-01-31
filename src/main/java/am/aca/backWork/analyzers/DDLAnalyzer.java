package am.aca.backWork.analyzers;

import java.sql.SQLException;

import am.aca.backWork.components.Schema;

public interface DDLAnalyzer<R> {

    Schema<R> getSchema() throws SQLException;

}
