package com.example.demo.controller;


import com.example.demo.model.dto.request.EditProfileAdminRequest;
import com.example.demo.model.dto.request.EditProfileRequest;
import com.example.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody EditProfileRequest request) {
        profileService.updateProfile(request.getFullName(), request.getEmail());
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/updateProfileByAdministrator")
    public ResponseEntity<?> updateProfileByAdministrator(@RequestBody EditProfileAdminRequest request) {
        return ResponseEntity.ok(profileService.updateProfileByAdministrator(request.getUserId(),
                request.getFullName(),
                request.getEmail(),
                request.getRoles()));
    }
}
