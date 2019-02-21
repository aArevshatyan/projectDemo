package am.aca.dbmigration.sql.generatedSQLs;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Messages about not supported features
 * and unable foreign keys
 */
@Component
@SessionScope
public class UnsupportedFeatures {
    private static List<String> unsupportedFeatures = new ArrayList<>();

    public static List<String> getUnsupportedFeatures() {
        return unsupportedFeatures;
    }

    public static void add(String s) {
        unsupportedFeatures.add(s);
    }

    public static void emptyList() {
        unsupportedFeatures = new ArrayList<>();
    }
}
