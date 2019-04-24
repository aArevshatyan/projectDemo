package am.aca.dbmigration.sql.generatedSQLs;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * DDL creating queries
 */
@Component
@SessionScope
public class GeneratedCreateSQLs {
    private static List<String> createSQLs = new ArrayList<>();

    public static List<String> getCreateSQLs() {
        return createSQLs;
    }

    public static void add(String s) {
        createSQLs.add(s);
    }

    public static void emptyList() {
        createSQLs = new ArrayList<>();
    }
}