package am.aca.dbmigration.sql.utils;

public class JdbcUrlHelper {
    public static String getDbType(String jdbcUrl) {
        int firstIndex = jdbcUrl.indexOf(":") + 1;
        return jdbcUrl.substring(firstIndex, jdbcUrl.indexOf(":", firstIndex));
    }

    public static String getDbName(String jdbcUrl) {
        int firstIndex = jdbcUrl.lastIndexOf("/") + 1;
        return jdbcUrl.substring(firstIndex, jdbcUrl.length());
    }
}
