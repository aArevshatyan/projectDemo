package am.aca.controllers;


import am.aca.backWork.testmain.Helper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
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
    public ModelAndView urlInput(HttpServletRequest httpServletRequest) {

        this.urlFrom = httpServletRequest.getParameter("urlFrom");
        this.usernameFrom = httpServletRequest.getParameter("usernameFrom");
        this.passwordFrom = httpServletRequest.getParameter("passwordFrom");

        return new ModelAndView("redirect:/schema");
    }

    ///// redirecta linum tablenery checkboxerov lcrac viewi mej u submit kochakov
    ///// heta galis eli viewi vra errornerov u accept u decline kochaknerov
    ///// 2i depqum el heta galis index.html.i vra accepti depqum successful messageov


    @GetMapping("/schema")
    public ModelAndView showTables() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("view");
        List tables = Helper.getSchema(urlFrom, usernameFrom, passwordFrom);
        modelAndView.addObject("tables", tables);
        return modelAndView;
    }


    /*@PostMapping("/schema")
    public ModelAndView generateSqlsAndNotSupportedFeatures() {

        return new ModelAndView("view.html");
    }*/

}
