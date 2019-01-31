package am.aca.backWork.components.utils;

import java.util.List;
import java.util.ArrayList;

public enum Type {

    INT("INT", "INT"),
    SMALLINT("SMALLINT", "SMALLINT"),
    INTEGER("INTEGER", "INT"),
    BIGINT("BIGINT", "BIGINT"),
    AUTOINCREMENT_INT("SERIAL", "INT"),
    AUTOINCREMENT_SMALLINT("SMALLSERIAL", "SMALLINT"),
    AUTOINCREMENT_BIG("INTBIGSERIAL", "BIGINT"),
    BIT("BIT", "BIT"),
    BOOLEAN("BOOLEAN", "TINYINT(1)"),
    FLOAT("REAL", "FLOAT"),
    DOUBLE("DOUBLE PRECISION", "DOUBLE"),
    NUMERIC("NUMERIC", "DECIMAL"),
    DECIMAL("DECIMAL", "DECIMAL"),
    MONEY("MONEY", "DECIMAL(19,2)"),
    CHAR("CHAR", "CHARACTER"),
    CHAR_VARYING("CHARACTER VARYING", "CHAR"),
    VARCHAR("VARCHAR", "VARCHAR"),
    VARCHAR_VARYING("VARCHAR VARYING", "VARCHAR"),
    MEDIUMTEXT("VARCHAR", "MEDIUMTEXT"),
    LONGTEXT("VARCHAR", "LONGTEXT"),
    DATE("DATE", "DATE"),
    TIME("TIME", "TIME"),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP"),
    TIMESTAMP_WITHOUT_TIME_ZONE("TIMESTAMP WITHOUT TIME ZONE", "TIMESTAMP"),
    DATETIME("TIMESTAMP", "DATETIME"),
    INTERVAL("INTERVAL", "TIME"),
    BYTEA("BYTEA", "LONGBLOB"),
    BLOB("BYTEA", "BLOB"),
    TEXT("TEXT", "LONGTEXT"),;


    private static List<String> mysql;
    private static List<String> postgres;

    public void addMysql(String s) {
        mysql.add(s);
    }

    ;

    public void addPostgresql(String s) {
        postgres.add(s);
    }

    ;

    public static List<String> getPostgres() {
        return postgres;
    }

    public static List<String> getMysql() {
        return mysql;
    }

    public static void setMysql(List<String> mysql) {
        Type.mysql = mysql;
    }

    public static void setPostgres(List<String> postgres) {
        Type.postgres = postgres;
    }

    Type(String pType, String mType) {
        if (getMysql() == null) setMysql(new ArrayList<>());
        if (getPostgres() == null) setPostgres(new ArrayList<>());
        addMysql(mType);
        addPostgresql(pType);
    }

    public static String getDataType(String from, String to, String myType) {

        List<String> fromList;
        List<String> toList;
        fromList = getStrings(from);
        toList = getStrings(to);

        for (int i = 0; i < fromList.size(); i++) {
            if (fromList.get(i).equals(myType)) return toList.get(i);
        }
        throw new UnsupportedOperationException( myType + "Unsupported Type");
    }

    private static List<String> getStrings(String dbType) {
        switch (dbType) {
            case "postgresql":
                return getPostgres();
            case "mysql":
                return getMysql();
            default:
                throw new UnsupportedOperationException("Unsupported RDBMS");
        }
    }
}
