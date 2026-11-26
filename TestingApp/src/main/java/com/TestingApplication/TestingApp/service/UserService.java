package com.TestingApplication.TestingApp.service;

import com.TestingApplication.TestingApp.dto.SignUpDto;
import com.TestingApplication.TestingApp.dto.UserDto;
import com.TestingApplication.TestingApp.entity.User;
import com.TestingApplication.TestingApp.exceptions.ResourceNotFoundException;
import com.TestingApplication.TestingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email  " + username + " not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new BadCredentialsException("User already exists with email " + signUpDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signUpDto, User.class);
        User savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
