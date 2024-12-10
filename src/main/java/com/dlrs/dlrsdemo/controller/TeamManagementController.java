package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.service.TeamService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/secure")
public class TeamManagementController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/createTeam")
    @ResponseBody
    public String createTeam(@RequestParam("name") String teamName,
                           @RequestParam("supervisor") Long supervisorId,
                           @RequestParam("surveyors[]") List<Long> surveyors){
        return teamService.createNewTeam(teamName, supervisorId, surveyors);
    }

    @PreAuthorize("hasAnyAuthority('super_admin', 'surveyor')")
    @PostMapping("/updateTeam")
    @ResponseBody
    public String teamUpdatation(@RequestParam("teamCode") Long teamCode,
                             @RequestParam("name") String teamName,
                           @RequestParam("supervisor") Long supervisorId,
                           @RequestParam("surveyors[]") List<Long> surveyors){
        return teamService.updateTeam(teamCode, teamName, supervisorId, surveyors);
    }


    


}
