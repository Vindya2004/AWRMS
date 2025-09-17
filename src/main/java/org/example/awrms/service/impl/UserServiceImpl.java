package org.example.awrms.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.awrms.dto.AuthDTO;
import org.example.awrms.dto.AuthResponseDTO;
import org.example.awrms.dto.UserDTO;
import org.example.awrms.entity.Role;
import org.example.awrms.entity.User;
import org.example.awrms.repository.UserRepository;
import org.example.awrms.service.UserService;
import org.example.awrms.util.JWTUtil;
import org.example.awrms.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private final UserRepository userRepository;
   @Autowired
   private ModelMapper modelMapper;


    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole("USER");
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDTO(
                    user.getName(),
                    user.getEmail(),
                    user.getContact(),
                    user.getPassword(),
                    user.getRole()
            );
        } else {
            return null; // Handle properly in the controller
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserRole(String email, String role) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(String email, UserDTO userDTO) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update allowed fields
            if (userDTO.getName() != null) {
                existingUser.setName(userDTO.getName());
            }
            if (userDTO.getContact() != null) {
                existingUser.setContact(userDTO.getContact());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            // Role update is usually restricted (handled separately by updateUserRole)
            // so we won’t set role here unless needed

            userRepository.save(existingUser);
            return true;
        }

        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<GrantedAuthority> getAuthority(User userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
        return authorities;
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }
}