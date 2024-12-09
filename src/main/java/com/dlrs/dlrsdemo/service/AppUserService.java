package com.dlrs.dlrsdemo.service;

import com.dlrs.dlrsdemo.common.UserRole;
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

    public String deleteUser(Long userId) {
        String res = "";
        boolean isExist = false;
        List<Team> teamsList = new ArrayList<>();
        AppUser userData = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found!"));
        if(userData.getRole() == UserRole.SUPER_ADMIN){
            res = "You can't delete the Super Admin!";
        }else if(userData.getRole() == UserRole.SUPERVISOR){
            teamsList = teamRepository.findAllBySupervisor(userData);
            if(teamsList.isEmpty()){
                userRepository.deleteById(userId);
                if(userRepository.existsById(userId)){
                    res = "Unable to delete the user";
                }else{
                    res = "Successfully deleted the user";
                }
            }else{
                res = "Failed! Supervisor is associated with team.";
            }
        }else{
            teamsList = teamRepository.findAll();
            for (Team team : teamsList){
                if(!team.getSurveyors().contains(userData) || !team.getSupervisor().equals(userData)){
                    userRepository.deleteById(userId);
                    if(userRepository.existsById(userId)){
                        res = "Unable to delete the user";
                    }else{
                        res = "Successfully deleted the user";
                    }
                }else{
                    res = "Unable to delete! This user associated with a team";
                }
            }

        }

        return res;
    }
}
