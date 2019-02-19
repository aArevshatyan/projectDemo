package am.aca.dbmigration.controllers;

import java.util.List;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

import am.aca.dbmigration.sql.generatedSQLs.*;
import am.aca.dbmigration.sql.tables.Table;
import am.aca.dbmigration.sql.MigrationData;
import am.aca.dbmigration.sql.SchemaAnalyzer;
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
        return new ModelAndView("redirect:/schema");
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

}
