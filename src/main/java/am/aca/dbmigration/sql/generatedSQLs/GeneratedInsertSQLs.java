package am.aca.dbmigration.sql.generatedSQLs;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * DML Data inserting queries
 */
@Component
@SessionScope
public class GeneratedInsertSQLs {
    private static List<String> insertSQLs = new ArrayList<>();

    public static void add(String s) {
        insertSQLs.add(s);
    }

    public static List<String> getGeneratedInsertSQLs() {
        return insertSQLs;
    }

    public static void emptyList() {
        insertSQLs = new ArrayList<>();
    }
}