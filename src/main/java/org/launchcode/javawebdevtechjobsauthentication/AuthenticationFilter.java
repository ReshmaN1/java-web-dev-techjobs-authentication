package org.launchcode.javawebdevtechjobsauthentication;

import org.launchcode.javawebdevtechjobsauthentication.controllers.AuthenticationController;
import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {
    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthenticationController authController;

    public static final List<String> whitelist = Arrays.asList("/login","/register","/logout");

    private static boolean isWhitelisted(String path){
        for(String pathRoot : whitelist){
            if(path.startsWith(pathRoot)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws IOException {
        if(isWhitelisted(req.getRequestURI())){
            return true;
        }

        HttpSession session = req.getSession();
        User user = authController.getUser(session);

        if(user != null){
            return true;
        }

        // The user is logged out
        res.sendRedirect("/login");
        return false;
    }
}
