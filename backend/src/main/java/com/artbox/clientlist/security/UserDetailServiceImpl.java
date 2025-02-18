package com.artbox.clientlist.security;

import com.artbox.clientlist.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.artbox.clientlist.model.User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found " + username);
        }
        com.artbox.clientlist.model.User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
