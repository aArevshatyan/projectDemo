package am.aca.backWork.converters;

import am.aca.backWork.components.Schema;

public interface Converter<T, R> {

    Schema<R> convert(Schema<T> schema);

}
