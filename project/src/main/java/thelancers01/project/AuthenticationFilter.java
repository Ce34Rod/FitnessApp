package thelancers01.project;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import thelancers01.project.controllers.AuthenticationController;
import thelancers01.project.models.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {


    public static String getUserFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/homepage", "/login", "/css", "/images", "/exerciseList", "/register", "/get", "/exercises","/create/exercise", "/userExercises");

    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.equals("/") || path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (isWhitelisted(request.getRequestURI())) {
            return true;
        }



//        Cookie userCookie = new Cookie("user", null);
//        userCookie.setPath("/"); // Set the path for which the cookie is valid
//        userCookie.setMaxAge(60 * 60); // Set cookie to expire in 7 days
//        userCookie.setHttpOnly(true); // Optional: Make the cookie HTTP only
//        response.addCookie(userCookie);

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        //SELF:I NEED TO CREATE A COOKIE NAMED "user" TO TEST AUTH FILTER

        if (user != null) {
            return true;
        }

        response.sendRedirect("/homepage");
        return false;
    }


    private boolean checkNecessaryCookies (HttpServletResponse response, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        // Check if cookies are null (meaning no cookies are present)
        if (cookies != null) {
            // Iterate through the cookies
            for (Cookie cookie : cookies) {
                // Check if the cookie name matches "username"
                if ("username".equals(cookie.getName())) {
                    // Cookie is found
                    return true;
                }
            }
        }

        // Cookie not found
        return false;
    }

}