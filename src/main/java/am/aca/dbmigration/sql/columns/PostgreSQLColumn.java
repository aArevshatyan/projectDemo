package am.aca.dbmigration.sql.columns;

import am.aca.dbmigration.sql.utils.Nullable;

public class PostgreSQLColumn {


    private String name;
    private int ordinalPosition;
    private String defaultValue;
    private Nullable isNullable;
    private String dataType;
    private int characterMaximumLength;
    private int characterOctetLength;
    private int numericPrecision;
    private int numericScale;
    private String type;

    public PostgreSQLColumn(String name, int ordinalPosition, String defaultValue,
                            Nullable isNullable, String dataType, int characterMaximumLength,
                            int characterOctetLength, int numericPrecision, int numericScale) {
        this.name = name;
        this.ordinalPosition = ordinalPosition;
        this.defaultValue = defaultValue;
        this.isNullable = isNullable;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
        this.characterOctetLength = characterOctetLength;
        this.numericPrecision = numericPrecision;
        this.numericScale = numericScale;

        if (numericPrecision != 0 && numericPrecision != 32) dataType = "Numeric";

        this.type = dataType +
                ((characterMaximumLength != 0 && characterMaximumLength != 65535 ) ?
                        ("(" + (characterMaximumLength) + ")")
                        : "") +
                ((numericPrecision != 0 && numericPrecision != 32) ?
                        ("(" + numericPrecision + ((numericScale != 0) ?
                                (" , " + numericScale + ")")
                                : ")") + "")
                        : "")
        ;
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

    public Nullable getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(Nullable isNullable) {
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

    @Override
    public String toString() {
        return "PostgreSQLColumn{" +
                "name='" + name + '\'' +
                ", datatype =" + type +
                " Nullable : " + isNullable.toString() +
                "}";
    }
}
