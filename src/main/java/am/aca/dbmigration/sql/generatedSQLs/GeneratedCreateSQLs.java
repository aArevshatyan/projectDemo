package am.aca.dbmigration.sql.generatedSQLs;

import java.util.ArrayList;
import java.util.List;

public class GeneratedCreateSQLs {
    private static List<String> createSQLs = new ArrayList<>();

    public static List<String> getCreateSQLs() {
        return createSQLs;
    }

    public static void add(String s) {
        createSQLs.add(s);
    }
}
