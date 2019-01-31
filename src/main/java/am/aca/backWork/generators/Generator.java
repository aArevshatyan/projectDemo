package am.aca.backWork.generators;

import am.aca.backWork.components.Schema;

import java.sql.SQLException;

public interface Generator<T> {

    void generateSQLOf(Schema<T> schema) throws SQLException;

}
