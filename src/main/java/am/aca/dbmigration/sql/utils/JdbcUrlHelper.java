package am.aca.dbmigration.sql.utils;

public class JdbcUrlHelper {
    /**
     * @param jdbcUrl is source or destination database url
     * @return type of database
     */
    public static String getDbType(String jdbcUrl) {
        int firstIndex = jdbcUrl.indexOf(":") + 1;
        return jdbcUrl.substring(firstIndex, jdbcUrl.indexOf(":", firstIndex));
    }

    /**
     * @param jdbcUrl is source or destination database url
     * @return name of database
     */
    public static String getDbName(String jdbcUrl) {
        int firstIndex = jdbcUrl.lastIndexOf("/") + 1;
        return jdbcUrl.substring(firstIndex, jdbcUrl.length());
    }
}