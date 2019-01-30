package am.aca.components.generatedSQLs;

import java.util.ArrayList;
import java.util.List;

public class GeneratedPrimarySQLs {
    private static List<String > primarySQLs = new ArrayList<>();

    public static void add (String s){
        primarySQLs.add(s);
    }
    public static List<String> getPrimarySQLs() {
        return primarySQLs;
    }
}
