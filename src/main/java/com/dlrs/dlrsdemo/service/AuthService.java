package com.dlrs.dlrsdemo.service;
import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;
    public Object registerNewUser(AppUser userData) {
        AppUser newUser = new AppUser();
        AppUser checkUser = appUserRepository.findByEmail(userData.getEmail());
        AppUser checkUserByPhone = appUserRepository.findByPhone(userData.getPhone());
        if(checkUser != null || checkUserByPhone != null){
            return "Email or Phone number already exist!";
        }
        else{
            newUser.setName(userData.getName());
            newUser.setEmail(userData.getEmail());
            newUser.setPhone(userData.getPhone());
            newUser.setPassword(passwordEncoder.encode(userData.getPassword()));
            newUser.setRole(userData.getRole());

            appUserRepository.save(newUser);

            return newUser;
        }

    }


    public Object createNewUser(JsonNode node) {
        AppUser newUser = new AppUser();
        AppUser checkUser = appUserRepository.findByEmail(node.get("email").asText());
        AppUser checkUserByPhone = appUserRepository.findByPhone(node.get("phone").asText());
        if(checkUser != null || checkUserByPhone != null){
            return "Email or Phone number already exist!";
        }
        else{
            newUser.setName(node.get("name").asText());
            newUser.setEmail(node.get("email").asText());
            newUser.setPhone(node.get("phone").asText());
            newUser.setPassword(passwordEncoder.encode(node.get("password").asText()));
            UserRole role = UserRole.getUserRoleType(node.get("role").asText());
            newUser.setRole(role);

            appUserRepository.save(newUser);

            return newUser;
        }

    }

}
