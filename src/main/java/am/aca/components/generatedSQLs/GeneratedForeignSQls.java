package am.aca.components.generatedSQLs;

import java.util.ArrayList;
import java.util.List;

public class GeneratedForeignSQls {
    private static List<String > foreignSQLs = new ArrayList<>();

    public static void add (String s){
        foreignSQLs.add(s);
    }
    public static List<String> getForeignSQLs() {
        return foreignSQLs;
    }
}
