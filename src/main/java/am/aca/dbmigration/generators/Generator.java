package am.aca.dbmigration.generators;

import am.aca.dbmigration.sql.Schema;

import java.sql.SQLException;

public interface Generator<T> {

    void generateSQLOf(Schema<T> schema) throws SQLException;

}
