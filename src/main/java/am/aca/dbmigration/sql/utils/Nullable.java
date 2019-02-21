package am.aca.dbmigration.sql.utils;

/**
 * Specifies nullability of column
 */
public enum Nullable {
    YES(""),
    NO("NOT NULL");

    private String nullable;

    Nullable(String nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return nullable;
    }
}
