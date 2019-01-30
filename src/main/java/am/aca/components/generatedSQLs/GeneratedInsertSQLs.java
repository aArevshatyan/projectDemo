package am.aca.components.generatedSQLs;

import java.util.ArrayList;
import java.util.List;

public class GeneratedInsertSQLs {
    private static List<String> insertSQLs = new ArrayList<>();


    public static void add(String s) {
        insertSQLs.add(s);
    }

    public static List<String> getGeneratedInsertSQLs() {
        return insertSQLs;
    }


}
