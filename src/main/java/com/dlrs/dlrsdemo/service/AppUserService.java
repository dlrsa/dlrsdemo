package com.dlrs.dlrsdemo.service;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;
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

}
