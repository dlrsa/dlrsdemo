package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.service.PattadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class FrontendController {

    @Autowired
    private PattadarService pattadarService;
    @GetMapping("/secure/getLocation")
    public String getLocation(Model model){
        List<Map<String, Object>> getLandClass = pattadarService.getAllLandClass();
        model.addAttribute("land", getLandClass);
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
