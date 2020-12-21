package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;
    public SignupController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(){
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute Users users, Model model){
        String signupError = null;
        if(!userService.isUsernameAvailable(users.getUsername())){
            signupError ="The username already exists.";
        }
        if(signupError==null){
            int rowAdded = userService.createUser(users);
            if(rowAdded <0){
                signupError = "Oppos!! something went wrong!!";
            }
        }
        if(signupError ==null){
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
