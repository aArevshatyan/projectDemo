package am.aca.generators;

import am.aca.components.Schema;

public interface Generator<T> {

    void generateSQLOf(Schema<T> schema);

}
