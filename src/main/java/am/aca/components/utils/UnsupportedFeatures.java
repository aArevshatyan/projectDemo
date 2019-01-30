package am.aca.components.utils;

import java.util.List;
import java.util.ArrayList;

public class UnsupportedFeatures {
    private static List<String> unsupportedFeatures = new ArrayList<>();

    public static List<String> getUnsupportedFeatures() {
        return unsupportedFeatures;
    }

    public static void add(String s) {
        unsupportedFeatures.add(s);
    }
}
