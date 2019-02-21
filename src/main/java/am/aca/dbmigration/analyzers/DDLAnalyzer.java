package am.aca.dbmigration.analyzers;

import am.aca.dbmigration.sql.Schema;
import am.aca.dbmigration.sql.tables.Table;

import java.sql.SQLException;


/**
 * @param <T> Source database type
 */
public interface DDLAnalyzer<T extends Table> {

    /**
     * The function is analyzing source database's information schema.
     * Creates a schema, that contains list of tables of database.
     * Initializes the tables by adding columns and constraints to it.
     * It is also specifies types of columns and constraints.
     *
     * @return Source database type schema
     * @throws SQLException
     */
    Schema<T> getSchema() throws SQLException;

}
