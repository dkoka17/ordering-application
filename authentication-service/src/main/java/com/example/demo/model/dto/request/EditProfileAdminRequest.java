package com.example.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileAdminRequest extends EditProfileRequest {
    List<String> roles;
    Long userId;

    public EditProfileAdminRequest(String fullName, String email, List<String> roles, Long userId) {
        super(fullName, email);
        this.roles = roles;
        this.userId = userId;
    }
}
