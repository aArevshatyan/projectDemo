package am.aca.dbmigration.converters;

import am.aca.dbmigration.sql.Schema;

/**
 * @param <T> Source database type
 * @param <R> Destination database type
 */
public interface Converter<T, R> {

    /**
     * This function takes already generated schema of
     * source database type and
     * convert into destination database type schema
     *
     * @param schema of source database type
     * @return destination database type schema
     */
    Schema<R> convert(Schema<T> schema);

}
