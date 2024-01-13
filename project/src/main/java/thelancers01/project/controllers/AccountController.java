package thelancers01.project.controllers;

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
import thelancers01.project.models.dto.ChangePasswordFormDTO;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/account")
    public String viewAccount(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("changePasswordFormDTO", new ChangePasswordFormDTO());
        return "account";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute @Valid ChangePasswordFormDTO changePasswordFormDTO, Errors errors, HttpSession session, Model model) {
        if (errors.hasErrors()) {

            return "account";
        }

        User user = getUserFromSession(session);
        if (user != null) {

            user.setPassword(changePasswordFormDTO.getNewPassword());
            userRepository.save(user);
        }

        return "passwordChangedConfirmation";
    }



    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session) {
        User user = getUserFromSession(session);
        if (user != null) {
            userRepository.delete(user);
            session.invalidate();
        }
        return "accountDeletionConfirmation";
    }

    private User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }
}
