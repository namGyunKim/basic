package org.example.basic.global.config.security.provider;

import lombok.RequiredArgsConstructor;
import org.example.basic.domain.account.model.UserDetailsImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = authenticationToken.getName();
        String password = (String) authentication.getCredentials();

        UserDetailsImpl account = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지않습니다.");
        }

        /*
         * checker
         * */

        if (!account.isAccountNonLocked()) {
            throw new LockedException("계정이 잠겼습니다. 관리자에게 문의하세요.");
        }

        if (!account.isEnabled()) {
            throw new DisabledException("계정이 비활성화 되었습니다. 관리자에게 문의하세요.");
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
