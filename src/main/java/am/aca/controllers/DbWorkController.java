package am.aca.controllers;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class DbWorkController {


    @GetMapping
    public ModelAndView homePage() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/schema")
    public ModelAndView showTables() {
        return new ModelAndView("view.html");
    }

}
