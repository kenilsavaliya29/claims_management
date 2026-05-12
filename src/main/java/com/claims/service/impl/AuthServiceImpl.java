package com.claims.service.impl;

import com.claims.DTOs.Request.LoginRequestDTO;
import com.claims.DTOs.Request.RegisterRequestDTO;
import com.claims.DTOs.Response.LoginResponseDTO;
import com.claims.DTOs.Response.RegisterResponseDTO;
import com.claims.repository.interfaces.UserRepository;
import com.claims.service.AuthService;
import com.claims.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkUserAlreadyExist(RegisterRequestDTO registerRequestDTO){
        return userRepository
                .findByEmail(registerRequestDTO.getEmail())
                .isPresent();
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(!checkUserAlreadyExist(registerRequestDTO)){

            String password = passwordEncoder.encode(registerRequestDTO.getPassword());

            User user = new User();

            user.setName(registerRequestDTO.getName());
            user.setEmail(registerRequestDTO.getEmail());
            user.setPassword(password);

            userRepository.save(user);
            return new RegisterResponseDTO("Registration Successful");
        }
        else{
            return new RegisterResponseDTO("Registration Already Exists");
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
                return new LoginResponseDTO("Login Successful");
            }
            else {
                return new LoginResponseDTO("Invalid Password");
            }
    }
}
