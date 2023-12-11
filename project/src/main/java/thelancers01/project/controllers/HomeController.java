package thelancers01.project.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String home() {
        return "homepage";
    }
}
