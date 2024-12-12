package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import com.dlrs.dlrsdemo.service.AppUserService;
import com.dlrs.dlrsdemo.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/secure")
public class SecureHomeController {
    @Autowired
    private AppUserService userService;

    @Autowired
    private TeamService teamService;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/home")
    public String getHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        System.out.println("user" + user);
        List<AppUser> superList = userService.getAllSupervisors();
        List<AppUser> survList = userService.getAllSurveyors();
        List<Team> teams = new ArrayList<>();
        Team team = new Team();

        if (user.getRole() == UserRole.SUPERVISOR) {
            teams = teamService.getAllTeamsForSupervisor(user);
            survList = userService.getOwnSurveyors(user);
        } else if (user.getRole() == UserRole.SURVEYOR) {
            teams = teamService.getAllTeamsForSurveyor(user);
            team = userService.getSurveyorTeam(user);
        } else {
            teams = teamService.getAllTeams();
        }

        System.out.println("Surv Team" + team);


        model.addAttribute("user", user);
        model.addAttribute("teams", teams);
        model.addAttribute("superCount", superList.size());
        model.addAttribute("survCount", survList.size());
        model.addAttribute("team", team);


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


        List<AppUser> userList = new ArrayList<>();
        if(user.getRole() == UserRole.SUPERVISOR){
            userList = userService.getOwnSurveyors(user);
        }else{
            userList = userService.getAllUsers();
        }

        model.addAttribute("user", user);
        model.addAttribute("userList", userList);
        return "/pages/secure/userlist";
    }

    @PreAuthorize("hasAnyAuthority('super_admin')")
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


    @GetMapping("/userDetails/{userId}")
    public String userDetails(@PathVariable Long userId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        AppUser userdata = appUserRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!"));
        List<Team> teams = new ArrayList<>();
        Team team = new Team();

        if (userdata.getRole() == UserRole.SUPERVISOR) {
            teams = teamService.getAllTeamsForSupervisor(userdata);
        } else if (userdata.getRole() == UserRole.SURVEYOR) {
            team = userService.getSurveyorTeam(userdata);
            if(team != null){
                teams.add(team);
            }else{
                teams = null;
            }
        } else {
            teams = teamService.getAllTeams();
        }

        model.addAttribute("user", user);
        model.addAttribute("userData", userdata);
        model.addAttribute("teams", teams);
//        model.addAttribute("team", team);
        return "/pages/secure/userdetails";
    }

}
