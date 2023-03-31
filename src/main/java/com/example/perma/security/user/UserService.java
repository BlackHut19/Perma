package com.example.perma.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found.", email)));
    }

    public User signUpUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new IllegalStateException("Email already taken");
        userRepository.save(user);
        return user;
    }


    public void enableUser(String email) {
        User user = loadUserByUsername(email);
        user.setIsEnabled(true);
    }
}
