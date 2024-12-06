package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.service.AppUserService;
import com.dlrs.dlrsdemo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/secure")
public class SecureHomeController {
    @Autowired
    private AppUserService userService;

    @Autowired
    private TeamService teamService;
    @GetMapping("/home")
    public String getHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        List<Team> teams = teamService.getAllTeams();

        model.addAttribute("user", user);
        model.addAttribute("teams", teams);
        return "/pages/secure/home";
    }

    @GetMapping("/createuser")
    public String createUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        Set<String> roles = UserRole.getUserRoleType();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "/pages/secure/createuser";
    }
    @GetMapping("/userlist")
    public String userList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        List<AppUser> userList = userService.getAllUsers();
        model.addAttribute("user", user);
        model.addAttribute("userList", userList);
        return "/pages/secure/userlist";
    }

    @GetMapping("/teamCreation")
    public String teamCreation(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        List<AppUser> superList = userService.getAllSupervisors();
        List<AppUser> survList = userService.getAllSurveyors();
        model.addAttribute("user", user);
        model.addAttribute("super", superList);
        model.addAttribute("surv", survList);
        return "/pages/secure/createteam";
    }

    @GetMapping("/teamDetails/{teamCode}")
    public String teamDetails(@PathVariable Long teamCode, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        Team team = teamService.getTeamById(teamCode);
        System.out.println("team" + team);
        List<AppUser> selectedSurveyors = team.getSurveyors();
        System.out.println("selectedSurveyors" + selectedSurveyors);
        List<AppUser> superList = userService.getAllSupervisors();
        List<AppUser> survList = userService.getAllSurveyors();
        model.addAttribute("user", user);
        model.addAttribute("team", team);
        model.addAttribute("super", superList);
        model.addAttribute("surv", survList);
        model.addAttribute("selectedSurveyors", selectedSurveyors);
        return "/pages/secure/teamdetails";
    }




}
