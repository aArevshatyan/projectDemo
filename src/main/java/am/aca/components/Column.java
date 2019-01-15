package am.aca.components;

public class Column {
    private String field;
    private String type; // enums for each db type?
    private String nullable;
    private String key; // PRI UNI MUL empty
    private String defaultValue;
    private String extra;

    public Column(String field, String type, String nullable, String key, String defaultValue, String extra) {

        this.field = field;
        this.type = type;
        this.nullable = nullable;
        this.key = key;
        this.defaultValue = defaultValue;
        this.extra = extra;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }

    public String getNullable() {
        return nullable;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "Column{" +
                "field='" + field + '\'' +
                '}';
    }
}
