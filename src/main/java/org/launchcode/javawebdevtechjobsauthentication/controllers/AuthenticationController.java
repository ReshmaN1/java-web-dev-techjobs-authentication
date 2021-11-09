package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.LoginFormDTO;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    public User getUser(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userid");
        if(userId == null){
            return null;
        }
        Optional<User> user = userRepository.findById(userId);
        if(user == null || user.isEmpty()){
            return null;
        }

        return user.get();
    }

    public static void setUserInSession(HttpSession session, User user){
        session.setAttribute("userid",user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model){
        model.addAttribute(new RegisterFormDTO());
        return "register";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model){
        model.addAttribute(new LoginFormDTO());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/login";
    }
}
