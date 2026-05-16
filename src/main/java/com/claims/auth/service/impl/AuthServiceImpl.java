package com.claims.auth.service.impl;

import com.claims.auth.dto.Request.LoginRequestDTO;
import com.claims.auth.dto.Request.RegisterRequestDTO;
import com.claims.auth.dto.Response.LoginResponseDTO;
import com.claims.auth.dto.Response.RegisterResponseDTO;
import com.claims.auth.repository.UserRepository;
import com.claims.security.jwt.JwtService;
import com.claims.auth.service.AuthService;
import com.claims.auth.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public boolean checkUserAlreadyExist(RegisterRequestDTO registerRequestDTO){
        return userRepository
                .findByEmail(registerRequestDTO.getEmail())
                .isPresent();
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(!checkUserAlreadyExist(registerRequestDTO)){

            String password = passwordEncoder.encode(registerRequestDTO.getPassword());
            String token = jwtService.generateToken(registerRequestDTO.getEmail());
            User user = new User();

            user.setName(registerRequestDTO.getName());
            user.setEmail(registerRequestDTO.getEmail());
            user.setPassword(password);

            userRepository.save(user);
            return new RegisterResponseDTO("Registration Successful",token);
        }
        else{
            return new RegisterResponseDTO("Email already exists");
        }
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
            Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());
            if(userOptional.isEmpty()){
                return new LoginResponseDTO("Invalid Email or Password");
            }

            User user = userOptional.get();
            boolean isPasswordMatch = passwordEncoder.matches(
                    loginRequestDTO.getPassword(),
                    user.getPassword()
            );

            if(isPasswordMatch){
                String token = jwtService.generateToken(loginRequestDTO.getEmail());
                return new LoginResponseDTO("Login Successful", token);
            }
            else {
                return new LoginResponseDTO("Invalid email or password");
            }
    }
}
