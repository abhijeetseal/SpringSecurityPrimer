package com.sealab.webservice.api.resourceserver.service;

import com.sealab.webservice.api.resourceserver.entity.CustomUserDetails;
import com.sealab.webservice.api.resourceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepo.findByUsername(username)!=null) {
            com.sealab.webservice.api.resourceserver.entity.User userDetails = userRepo.findByUsername(username);
            return new User(userDetails.getUsername(), userDetails.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


}
