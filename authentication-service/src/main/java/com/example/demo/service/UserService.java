package com.example.demo.service;

import com.example.demo.config.JwtUtils;
import com.example.demo.model.dto.exception.ApiException;
import com.example.demo.model.dto.request.AuthenticateRequest;
import com.example.demo.model.dto.request.RegisterRequest;
import com.example.demo.model.dto.response.JwtDTO;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Errors;
import com.example.demo.model.enums.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    public JwtDTO authenticate(AuthenticateRequest authenticateRequest) {
        Authentication authentication = authenticateUser(authenticateRequest.getEmail(), authenticateRequest.getPassword());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = generateToken(authentication);

        return new JwtDTO(token,
                userDetails.getId(),
                userDetails.getEmail(),
                roles);
    }

    public JwtDTO register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiException(Errors.EMAIL_ALREADY_REGISTERED.getValue());
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .build();

        addRoles(user, registerRequest.getRoles());

        userRepository.save(user);

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        String token = generateToken(registerRequest.getEmail(),registerRequest.getPassword());

        return new JwtDTO(token,
                user.getUserId(),
                user.getEmail(),
                roles);
    }

    private String generateToken(String email, String password){

        Authentication authentication = authenticateUser(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Bearer " + jwtUtils.generateJwtToken(authentication);
    }

    private String generateToken(Authentication authentication){

        return "Bearer " + jwtUtils.generateJwtToken(authentication);
    }

    private Authentication authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private void addRoles(User user, List<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "ROLE_SELLER":
                    roles.add(roleService.getRoleByName(UserRole.ROLE_SELLER));
                    break;
                case "ROLE_CLIENT":
                    roles.add(roleService.getRoleByName(UserRole.ROLE_CLIENT));
                    break;
                case "ROLE_ADMINISTRATOR":
                    roles.add(roleService.getRoleByName(UserRole.ROLE_ADMINISTRATOR));
                    break;
                default: break;
            }
        });
        user.setRoles(roles);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found!"));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        return userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found!"));
    }

}
