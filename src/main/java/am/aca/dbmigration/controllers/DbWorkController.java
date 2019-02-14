package am.aca.dbmigration.controllers;


import am.aca.dbmigration.sql.SchemaAnalyzer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@Controller
@SessionScope
public class DbWorkController {

    private String urlFrom;
    private String usernameFrom;
    private String passwordFrom;
    private String urlTo;
    private String usernameTo;
    private String passwordTo;

    @GetMapping("/")
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }

    @PostMapping("/")
    public ModelAndView urlToInput(HttpServletRequest httpServletRequest) {

        this.urlFrom = httpServletRequest.getParameter("urlFrom");
        this.usernameFrom = httpServletRequest.getParameter("usernameFrom");
        this.passwordFrom = httpServletRequest.getParameter("passwordFrom");
        return new ModelAndView("redirect:/schema");
    }


    @GetMapping("/schema")
    public ModelAndView showTables() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("view");
        List tables = SchemaAnalyzer.getSchema(urlFrom, usernameFrom, passwordFrom);
        modelAndView.addObject("tables", tables);
        return modelAndView;
    }

/*
    @PostMapping("/schema")
    public ModelAndView urlFromInput(HttpServletRequest httpServletRequest) {
        this.urlFrom = httpServletRequest.getParameter("urlFrom");
        this.usernameFrom = httpServletRequest.getParameter("usernameFrom");
        this.passwordFrom = httpServletRequest.getParameter("passwordFrom");

        return new ModelAndView("view.html");
    }*/

}
