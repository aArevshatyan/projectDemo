package am.aca.dbmigration.sql;

import am.aca.dbmigration.sql.tables.Table;

public class MigrationData {
    public static String urlFrom;
    public static String usernameFrom;
    public static String passwordFrom;
    public static String urlTo;
    public static String usernameTo;
    public static String passwordTo;
    public static Schema<? extends Table> schemaFrom;
    public static Schema schemaTo;

    @Override
    public String toString() {
        return "MigrationData{" +
                "urlFrom='" + urlFrom + '\'' +
                ", usernameFrom='" + usernameFrom + '\'' +
                ", passwordFrom='" + passwordFrom + '\'' +
                ", urlTo='" + urlTo + '\'' +
                ", usernameTo='" + usernameTo + '\'' +
                ", passwordTo='" + passwordTo + '\'' +
                '}';
    }
}
