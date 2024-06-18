package thelancers01.project.controllers;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import thelancers01.project.models.User;
import thelancers01.project.models.data.UserRepository;
import thelancers01.project.models.dto.LoginFormDTO;
import thelancers01.project.models.dto.RegisterFormDTO;

import java.util.Enumeration;
import java.util.Optional;

import static java.lang.System.out;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return null;
        }
        return userOpt.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
        out.println(session.getId());
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Object value = session.getAttribute(key);
            out.println(key + ": " + value + "<br>");
        }
        out.println(userSessionKey+user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {

        model.addAttribute("registerFormDTO", new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors, HttpServletResponse response, HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "register");
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUserName());

        if (existingUser != null) {
            errors.rejectValue("userName", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "register");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "register");
            return "register";
        }

        User newUser = new User(registerFormDTO.getUserName(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);



        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request, Model model, HttpServletResponse response) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUserName());

        String password = loginFormDTO.getPassword();

        if (theUser != null && theUser.isMatchingPassword(password)) {
            setUserInSession(request.getSession(), theUser);

            out.println(theUser.getUsername() + "Login cookie");

            // Create a cookie
            Cookie userCookie = new Cookie("user", theUser.getUsername());
            userCookie.setPath("/"); // Set the path for which the cookie is valid
            userCookie.setMaxAge(60 * 60); // Set cookie to expire in 7 days
            userCookie.setHttpOnly(true); // Optional: Make the cookie HTTP only
            response.addCookie(userCookie);

            return "redirect:/dashboard";
        } else {
            errors.rejectValue("password", "login.invalid", "Invalid username or password");
            model.addAttribute("title", "Log In");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Cookie cookie = new Cookie("user", null);

        // Set the cookie to expire immediately (set maxAge to 0)
        cookie.setMaxAge(0);

        // Set the cookie path, make sure it matches the path of the original cookie
        cookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(cookie);

        request.getSession(false).invalidate();


        return "redirect:/";
    }

}
