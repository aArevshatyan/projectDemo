package am.aca.dbmigration.controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

import am.aca.dbmigration.sql.generatedSQLs.*;
import am.aca.dbmigration.sql.tables.Table;
import am.aca.dbmigration.sql.MigrationData;
import am.aca.dbmigration.sql.SchemaAnalyzer;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
@SessionScope
public class DbWorkController {


    @GetMapping("/")
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }

    @PostMapping("/")
    public ModelAndView urlToInput(HttpServletRequest httpServletRequest) {

        MigrationData.urlFrom = httpServletRequest.getParameter("urlFrom");
        MigrationData.usernameFrom = httpServletRequest.getParameter("usernameFrom");
        MigrationData.passwordFrom = httpServletRequest.getParameter("passwordFrom");

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("testMsg","Please input correct database info");

        return (testConnection("from")) ? new ModelAndView("redirect:/schema") : modelAndView;
    }

    @GetMapping("/schema")
    public ModelAndView showTables() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("sessionId", RequestContextHolder.currentRequestAttributes().getSessionId());
        List<? extends Table> tables = SchemaAnalyzer.getSchema(MigrationData.urlFrom, MigrationData.usernameFrom, MigrationData.passwordFrom);
        modelAndView.addObject("tables", tables);
        return modelAndView;
    }


    @PostMapping("/prepare")
    public ResponseEntity<?> prepareSqls(HttpServletRequest httpServletRequest) {
        MigrationData.urlTo = httpServletRequest.getParameter("urlTo");
        MigrationData.usernameTo = httpServletRequest.getParameter("usernameTo");
        MigrationData.passwordTo = httpServletRequest.getParameter("passwordTo");
        String checkedTables = httpServletRequest.getParameter("checkedTables");
        SchemaAnalyzer.setEnabled(checkedTables);

        return ResponseEntity.ok().build();
    }



    private boolean testConnection(String s) {
        switch (s) {
            case "from": {
                try {
                    DriverManager.getConnection(
                            MigrationData.urlFrom,
                            MigrationData.usernameFrom,
                            MigrationData.passwordFrom
                    );
                } catch (SQLException e) {
                    return false;
                }
                return true;
            }
            case "to": {
                try {
                    DriverManager.getConnection(
                            MigrationData.urlTo,
                            MigrationData.usernameTo,
                            MigrationData.passwordTo
                    );
                } catch (SQLException e) {
                    return false;
                }
                return true;
            }
            default:
                return false;
        }
    }
}
