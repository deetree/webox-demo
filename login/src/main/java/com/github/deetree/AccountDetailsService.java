package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mariusz Bal
 */
@Service
class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }
}
