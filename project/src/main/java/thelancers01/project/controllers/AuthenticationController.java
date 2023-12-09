package thelancers01.project.controllers;


import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Optional;

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
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors, HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUserName());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "register";
        }
        //TODO: Verify both passwords inputted match
        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User newUser = new User(registerFormDTO.getUserName(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
        return "redirect:/homepage";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUserName());

        String password = loginFormDTO.getPassword();

        if (theUser == null || theUser.isMatchingPassword(password)) {
            errors.rejectValue("password",
                    "login.invalid",
                    "Credentials invalid. Please try again with correct username/password combination");
            return "login";
        }

        setUserInSession(request.getSession(), theUser);
        return "redirect:/homepage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/homepage";
    }

}
