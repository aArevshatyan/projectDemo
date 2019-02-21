package am.aca.dbmigration.sql.generatedSQLs;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Primary key setting queries
 */
@Component
@SessionScope
public class GeneratedPrimarySQLs {
    private static List<String> primarySQLs = new ArrayList<>();

    public static void add(String s) {
        primarySQLs.add(s);
    }

    public static List<String> getPrimarySQLs() {
        return primarySQLs;
    }

    public static void emptyList() {
        primarySQLs = new ArrayList<>();
    }
}
