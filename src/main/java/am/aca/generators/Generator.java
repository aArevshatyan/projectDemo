package am.aca.generators;

import am.aca.components.Schema;

import java.sql.SQLException;

public interface Generator<T> {

    void generateSQLOf(Schema<T> schema) throws SQLException;

}
