package am.aca.backWork.testmain;

import am.aca.backWork.analyzers.DDLAnalyzerFactory;

import java.sql.SQLException;
import java.util.List;

public class Helper {
    public static List getSchema(String urlFrom, String usernameFrom, String passwordFrom) throws SQLException {
        return DDLAnalyzerFactory.
                getAnalyzer(urlFrom, usernameFrom, passwordFrom)
                .getSchema().getTables();

    }

}
