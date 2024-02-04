package org.example.basic.global.config.security.checker;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class AccountStatusUserDetailsChecker implements UserDetailsChecker {
    @Override
    public void check(UserDetails toCheck) {
        if (!toCheck.isAccountNonLocked()) {
            throw new LockedException("");
        }
        if (!toCheck.isEnabled()) {
            throw new DisabledException("");
        }
    }
}
