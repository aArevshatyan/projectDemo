package am.aca.converters;

import am.aca.components.Schema;

public interface Converter<T, R> {

    Schema<R> convert(Schema<T> schema);

}
