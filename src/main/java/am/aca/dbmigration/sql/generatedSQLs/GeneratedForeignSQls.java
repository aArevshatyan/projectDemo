package am.aca.dbmigration.sql.generatedSQLs;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Foreign key setting queries
 */
@Component
@SessionScope
public class GeneratedForeignSQls {
    private static List<String> foreignSQLs = new ArrayList<>();

    public static List<String> getForeignSQLs() {
        return foreignSQLs;
    }

    public static void add(String s) {
        foreignSQLs.add(s);
    }

    public static void emptyList() {
        foreignSQLs = new ArrayList<>();
    }
}