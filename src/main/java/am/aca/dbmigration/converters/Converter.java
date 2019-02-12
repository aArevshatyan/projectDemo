package am.aca.dbmigration.converters;

import am.aca.dbmigration.sql.Schema;

public interface Converter<T, R> {

    Schema<R> convert(Schema<T> schema);

}
