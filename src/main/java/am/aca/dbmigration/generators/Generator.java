package am.aca.dbmigration.generators;

import am.aca.dbmigration.sql.Schema;

import java.sql.SQLException;

/**
 * @param <T> Destination database type
 */
public interface Generator<T> {
    /**
     * By already generated destination type schema it generates
     * DDL queries and warning messages and stored them into special lists
     *
     * @param schema Destination database type schema
     * @throws SQLException
     */
    void generateSQLOf(Schema<T> schema) throws SQLException;
}