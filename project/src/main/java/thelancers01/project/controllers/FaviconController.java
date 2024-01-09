package thelancers01.project.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaviconController {
    @GetMapping("favicon.ico")
    public void favicon(HttpServletResponse response) {
        // Set appropriate headers or return a default favicon
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

