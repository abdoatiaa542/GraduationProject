package com.abdoatiia542.GraduationProject.Impl;

import com.abdoatiia542.GraduationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.loadByUsernameOrEmail(username.strip())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with studentId or email %s not found."
                                .formatted(username)
                ));
    }
}