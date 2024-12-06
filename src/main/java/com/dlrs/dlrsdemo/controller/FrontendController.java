package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.common.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class FrontendController {
    @GetMapping("/secure/getLocation")
    public String getLocation(){
        return "/pages/getLocation";
    }

    @GetMapping("/")
    public String home(Model model){
        Set<String> roles = UserRole.getUserRoleType();
        model.addAttribute("roles", roles);
        return "/pages/login";
    }

    @GetMapping("/register")
    public String login(Model model){
        Set<String> roles = UserRole.getUserRoleType();
        model.addAttribute("roles", roles);
        return "/home";

    }

}
