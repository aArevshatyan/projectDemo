package am.aca.dbmigration.sql.generatedSQLs;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

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
        createSQLs= new ArrayList<>();
    }
}
