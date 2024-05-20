package org.pao.audiolibrarypao.services;

import java.util.List;
import java.util.Optional;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional
                .map(
                        user ->
                                new org.springframework.security.core.userdetails.User(
                                        user.getEmail(),
                                        user.getPassword(),
                                        List.of((GrantedAuthority) () -> "ROLE_USER")))
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
