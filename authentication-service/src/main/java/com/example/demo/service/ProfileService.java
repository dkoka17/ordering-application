package com.example.demo.service;

import com.example.demo.model.dto.exception.ApiException;
import com.example.demo.model.dto.response.GetProfileResponse;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Errors;
import com.example.demo.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public GetProfileResponse getProfile() {
        User user = userService.getAuthenticatedUser();
        return new GetProfileResponse(user);
     }

    public void updateProfile(String fullName, String email) {
        this.updateProfile(userService.getAuthenticatedUser().getUserId(), fullName, email,userService.getAuthenticatedUser().getRoles());
    }

    public User updateProfileByAdministrator(Long userId, String fullName, String email, List<String> rolesStr) {
        Set<Role> roles = rolesStr.stream().map(roleStr -> roleService.getRoleByName(UserRole.fromValue(roleStr))).collect(Collectors.toSet());
        updateProfile(userId,fullName,email,roles);
        return userService.getById(userId);
    }

    private void updateProfile(Long userId, String fullName, String email, Set<Role> roles){
        if (fullName.isEmpty() || email.isEmpty() || roles.isEmpty()) {
            throw new ApiException(Errors.INCOMPLETE_INFORMATION.getValue());
        }
        User user = userService.getById(userId);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRoles(roles);

        userService.saveUser(user);
    }
}
