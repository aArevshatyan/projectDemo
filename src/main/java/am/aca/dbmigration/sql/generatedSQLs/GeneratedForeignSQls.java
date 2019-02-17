package am.aca.dbmigration.sql.generatedSQLs;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class GeneratedForeignSQls {
    private static List<String > foreignSQLs = new ArrayList<>();

    public static List<String> getForeignSQLs() {
        return foreignSQLs;
    }

    public static void add (String s){
        foreignSQLs.add(s);
    }
}
