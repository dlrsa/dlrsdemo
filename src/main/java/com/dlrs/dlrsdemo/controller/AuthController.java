package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.dto.ResponseDto;
import com.dlrs.dlrsdemo.dto.UserLoginDto;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import com.dlrs.dlrsdemo.repository.AppUserRepository;
import com.dlrs.dlrsdemo.service.AppUserService;
import com.dlrs.dlrsdemo.service.AuthService;
import com.dlrs.dlrsdemo.service.CustomUserDetailsServiceImpl;
import com.dlrs.dlrsdemo.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser userData){
        System.out.println("userData" + userData);
        Object user = authService.registerNewUser(userData);
        if (user instanceof String) {  // If the result is a message indicating failure
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);  // Return 409 Conflict
        } else {
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);  // Return 201 Created
        }
    }

    @PostMapping("/secure/createUser")
    public ResponseEntity<?> createUser(@RequestBody JsonNode node){
        System.out.println("userData" + node);
        Object user = authService.createNewUser(node);
        if (user instanceof String) {  // If the result is a message indicating failure
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);  // Return 409 Conflict
        } else {
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);  // Return 201 Created
        }
    }


    @PostMapping("/userlogin")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto credentials, HttpServletResponse response) {
        System.out.println("##################Login Credentials###################" + credentials);
        AppUser userFind = null;
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(credentials.getEmail());

            System.out.println("User Details - " + userDetails);

            AppUser activeUser = userService.findByEmail(userDetails.getUsername());


            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            Cookie authCookie = new Cookie("authToken", jwtToken);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Use true if your app is HTTPS
            authCookie.setPath("/");

            response.addCookie(authCookie);

            userFind = userService.findByEmail(userDetails.getUsername());

            return new ResponseEntity<>(jwtToken, HttpStatus.OK);




        } catch (Exception e) {
            return new ResponseEntity<>(userFind, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/secure/userlogout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Invalidate the session to remove the JSESSIONID
            request.getSession().invalidate();

            // Remove the JSESSIONID cookie by setting its max age to 0
            Cookie jsessionidCookie = new Cookie("JSESSIONID", null);
            jsessionidCookie.setPath("/");
            jsessionidCookie.setMaxAge(0);
            response.addCookie(jsessionidCookie);

            // Remove the authToken cookie as well
            Cookie authCookie = new Cookie("authToken", null);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Use true if your app is HTTPS
            authCookie.setPath("/");
            authCookie.setMaxAge(0);
            response.addCookie(authCookie);

            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Logout failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/secure/deleteUser")
    @ResponseBody
    public ResponseDto deleteUser(@RequestParam Long userId){
        String res = "initiated";
        ResponseDto responseData = new ResponseDto();
        System.out.println(userId);
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        if(user.getRole() == UserRole.SURVEYOR){
            Team team = userService.getSurveyorTeam(user);
            if(team == null){
                appUserRepository.delete(user);
                responseData.setResCode("Success");
                responseData.setResponse("Surveyor Deleted Successfully");
            }else{
                responseData.setResCode("Failed");
                responseData.setResponse("Unable to delete the Surveyor! Surveyor is already associated with a team");
            }
        }else{
            responseData = userService.deleteUser(userId);
        }

        return responseData;
    }
}
