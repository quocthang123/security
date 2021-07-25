package com.spring.jpa.sercurity.jwt.study_project.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.sercurity.jwt.study_project.dto.request.LoginRequest;
import com.spring.jpa.sercurity.jwt.study_project.dto.request.SignupRequest;
import com.spring.jpa.sercurity.jwt.study_project.dto.response.JwtResponse;
import com.spring.jpa.sercurity.jwt.study_project.dto.response.MessageResponse;
import com.spring.jpa.sercurity.jwt.study_project.entity.AppRole;
import com.spring.jpa.sercurity.jwt.study_project.entity.AppUser;
import com.spring.jpa.sercurity.jwt.study_project.entity.EAppRole;
import com.spring.jpa.sercurity.jwt.study_project.repository.AppRoleRepository;
import com.spring.jpa.sercurity.jwt.study_project.repository.AppUserRepository;
import com.spring.jpa.sercurity.jwt.study_project.service.UserDetailImpl;
import com.spring.jpa.sercurity.jwt.study_project.util.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api}/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid  SignupRequest signUpRequest) {
        if (appUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        AppUser appUser = new AppUser(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail());

        Set<String> strRoles = signUpRequest.getRole();
        Set<AppRole> roles = new HashSet<>();

        if (strRoles == null) {
            AppRole userRole = appRoleRepository.findByRoleName(EAppRole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "admin":
                    AppRole adminRole = appRoleRepository.findByRoleName(EAppRole.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "mod":
                    AppRole modRole = appRoleRepository.findByRoleName(EAppRole.MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    AppRole userRole = appRoleRepository.findByRoleName(EAppRole.USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        appUser.setAppRole(roles);
        appUser.setFirstName(signUpRequest.getFirstName());
        appUser.setLastName(signUpRequest.getLastName());
        appUser.setCounter(0);
        appUser.setDelete(false);
        appUser.setLastLogin(new Date());
        appUser.setStatus("0");
        appUser.setStatus("");
        appUserRepository.save(appUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
