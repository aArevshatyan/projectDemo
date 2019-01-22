package am.aca.components.columns;

public class MySQLColumn {

    private String name;
    private int ordinalPosition;
    private String defaultValue;
    private String isNullable;
    private String dataType;
    private int characterMaximumLength;
    private int characterOctetLength;
    private int numericPrecision;
    private int numericScale;
    private String type;
    private String key;

    public MySQLColumn(String name, int ordinalPosition, String defaultValue,
                       String isNullable, String dataType, int characterMaximumLength,
                       int characterOctetLength, int numericPrecision, int numericScale,
                       String type, String key) {
        this.name = name;
        this.ordinalPosition = ordinalPosition;
        this.defaultValue = defaultValue;
        this.isNullable = isNullable;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
        this.characterOctetLength = characterOctetLength;
        this.numericPrecision = numericPrecision;
        this.numericScale = numericScale;
        this.type = type;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(int characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public int getCharacterOctetLength() {
        return characterOctetLength;
    }

    public void setCharacterOctetLength(int characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    public int getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(int numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public int getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(int numericScale) {
        this.numericScale = numericScale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "MySQLColumn{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
