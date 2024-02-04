package org.example.basic.domain;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basic.domain.account.constant.Role;
import org.example.basic.domain.account.entity.Account;
import org.example.basic.domain.account.payload.request.SaveAccountRequest;
import org.example.basic.domain.account.repository.AccountRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile(value = {"local"})
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostConstruct
    public void init() {
        initService.createdAccount();
    }

    @Service
    @RequiredArgsConstructor
    @Transactional(readOnly = true)
    static class InitService {
        private final AccountRepository accountRepository;

        @Transactional
        public void createdAccount() {
            if (accountRepository.count() == 0) {
                SaveAccountRequest saveAccountRequest = new SaveAccountRequest("최고관리자", "admin", "1234",Role.ADMIN);
                saveAccountRequest.encodePassword(passwordEncoder.encode(saveAccountRequest.getPassword()));
                Account account = new Account(saveAccountRequest);
                account.unlockAccount();
                accountRepository.save(account);
            }
        }

    }
}


