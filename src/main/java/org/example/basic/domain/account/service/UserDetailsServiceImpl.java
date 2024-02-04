package org.example.basic.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.example.basic.domain.account.entity.Account;
import org.example.basic.domain.account.model.UserDetailsImpl;
import org.example.basic.domain.account.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new UserDetailsImpl(account);
    }
}
