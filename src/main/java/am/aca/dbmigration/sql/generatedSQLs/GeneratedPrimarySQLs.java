package am.aca.dbmigration.sql.generatedSQLs;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class GeneratedPrimarySQLs {
    private static List<String > primarySQLs = new ArrayList<>();

    public static void add (String s){
        primarySQLs.add(s);
    }

    public static List<String> getPrimarySQLs() {
        return primarySQLs;
    }
}
