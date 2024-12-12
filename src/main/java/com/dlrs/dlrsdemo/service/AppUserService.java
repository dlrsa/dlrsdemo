package com.dlrs.dlrsdemo.service;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.dto.ResponseDto;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import com.dlrs.dlrsdemo.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;

    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public List<AppUser> getAllSupervisors() {
        return userRepository.findAllByRole(UserRole.SUPERVISOR);
    }

    public List<AppUser> getAllSurveyors() {
        return userRepository.findAllByRole(UserRole.SURVEYOR);

    }


    @PreAuthorize("hasAuthority('super_admin')")
    public ResponseDto deleteUser(Long userId) {
        String res = "";
        ResponseDto responseData = new ResponseDto();
        boolean isExist = true;
        List<Team> teamsList = new ArrayList<>();
        AppUser userData = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found!"));
        if(userData.getRole() == UserRole.SUPER_ADMIN){
            responseData.setResCode("Failed");
            responseData.setResponse("You can't delete the Super Admin!");
        }else if(userData.getRole() == UserRole.SUPERVISOR){
            teamsList = teamRepository.findAllBySupervisor(userData);
            if(teamsList.isEmpty()){
                userRepository.deleteById(userId);
                if(userRepository.existsById(userId)){
                    responseData.setResCode("Failed");
                    responseData.setResponse("Unable to delete the user");
                }else{
                    responseData.setResCode("Success");
                    responseData.setResponse("Successfully deleted the user");
                }
            }else{
                responseData.setResCode("Failed");
                responseData.setResponse("Failed! Supervisor is associated with team");
            }
        }

        return responseData;
    }

    public List<AppUser> getOwnSurveyors(AppUser user) {
        List<Team> teams = teamRepository.findAllBySupervisor(user);
        List<AppUser> surveyorsList = new ArrayList<>();
        for (Team team : teams){
           surveyorsList.addAll(team.getSurveyors());
        }
        return surveyorsList;
    }

    public Team getSurveyorTeam(AppUser user) {
        Team team1 = new Team();
        team1 = null;
        List<Team> teamList = teamRepository.findAll();
        for (Team team : teamList){
            if(team.getSurveyors().contains(user)){
                team1 = teamRepository.findById(team.getTeamId()).orElseThrow(() -> new EntityNotFoundException("Team Not Found"));
                break;
            }else {
                team1 = null;
            }
        }
        return team1;
    }
}
