package com.dlrs.dlrsdemo.service;

import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import com.dlrs.dlrsdemo.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private AppUserRepository userRepo;

    public String createNewTeam(String name, Long supervisorId, List<Long> surveyorIds) {
        // Save team details (e.g., in a "teams" table)
        String response = "";
        boolean isExist = false;
        String flag = "I";
        Team team = new Team();
        team.setTeamName(name);
        AppUser superVisor = new AppUser();
        superVisor.setUserId(supervisorId);
        team.setSupervisor(superVisor);
        List<AppUser> surveyorList = new ArrayList<>();

        // Save surveyor assignments (e.g., in a "team_surveyors" table)
        for (Long surveyorId : surveyorIds) {
            AppUser surveyor = userRepo.findById(surveyorId).orElseThrow(() -> new EntityNotFoundException("Unable to found"));
            surveyorList.add(surveyor);
        }
        for (AppUser surveyor : surveyorList) {
            List<Team> teamsData = teamRepo.findAll();
            for (Team teamDetails : teamsData) {
                if (teamDetails.getSurveyors().contains(surveyor)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                flag = "Y";
            } else {
                flag = "N";
                response = surveyor.getName();
            }

        }

        if (flag == "Y") {
            addSurveyors(team, surveyorList);
            response = "Team Created Successfully";
        } else {
            response = "The Surveyor " + response + " is already associated with another team";
        }

        return response;
    }


    private void addSurveyors(Team team, List<AppUser> surveyorList) {
        team.setSurveyors(surveyorList);
        teamRepo.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepo.findAll();
    }

    public Team getTeamById(Long teamId) {
        return teamRepo.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team Not Found!"));
    }


    public String updateTeam(Long teamCode, String name, Long supervisorId, List<Long> surveyorIds) {
        String response = "";
        boolean isExist = false;
        Team team = teamRepo.findById(teamCode).orElseThrow(() -> new EntityNotFoundException("Team Not Found!"));
        team.setTeamName(name);
        AppUser superVisor = new AppUser();
        superVisor.setUserId(supervisorId);
        team.setSupervisor(superVisor);
        team.getSurveyors().clear();
        List<AppUser> surveyorList = new ArrayList<>();

        if (!surveyorIds.isEmpty()) {
            for (Long surveyorId : surveyorIds) {
                AppUser surveyor = userRepo.findById(surveyorId).orElseThrow(() -> new EntityNotFoundException("Unable to found"));
                surveyorList.add(surveyor);
            }
        } else {
            team.getSurveyors().clear();
        }


        for (AppUser surveyor : surveyorList) {
            List<Team> teamsData = teamRepo.findAll();
            for (Team teamDetails : teamsData) {
                if (teamDetails.getSurveyors().contains(surveyor)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                addSurveyors(team, surveyorList);
                response = "Team " + team.getTeamName() + " Updated Successfully";
            } else {
                response = "The Surveyor " + surveyor.getName() + " is already associated with another team";
            }

        }

        return response;
    }

    public List<Team> getAllTeamsForSupervisor(AppUser user) {
        return teamRepo.findAllBySupervisor(user);
    }

    public List<Team> getAllTeamsForSurveyor(AppUser user) {
        List<Team> tm = teamRepo.findAll();
        List<Team> surveyorTeam = new ArrayList<>();
        for(Team team : tm){
            if(team.getSurveyors().contains(user)){
                surveyorTeam.add(team);
            }
        }
        return surveyorTeam;
    }
}
