package com.example.demo.model.dto.response;

import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileResponse {
    private String fullName;
    private String email;
    List<String> roles;
    Long userId;


    public GetProfileResponse(User user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.userId = user.getUserId();
        List<String> roles = user.getRoles().stream().map(
                role -> role.getName().toString()
        ).collect(Collectors.toList());
        this.roles = roles;
    }
}
