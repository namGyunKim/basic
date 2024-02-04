package org.example.basic.global.config.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.example.basic.domain.account.constant.Role;
import org.example.basic.global.config.security.hanlder.CustomLoginFailureHandler;
import org.example.basic.global.config.security.provider.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private static final RequestMatcher AUTH_WHITELIST = new OrRequestMatcher(
            PathRequest.toStaticResources().atCommonLocations(),
            new AntPathRequestMatcher("/login"),
            new AntPathRequestMatcher("/api/**"),
            new AntPathRequestMatcher("/assets/**")
    );

    private static final RequestMatcher AUTH_ADMIN_REQUIRED = new OrRequestMatcher(
            new AntPathRequestMatcher("/admin/**")
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request
                        -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD)
                        .authenticated().requestMatchers(AUTH_ADMIN_REQUIRED).hasAuthority(Role.ADMIN.name())
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .formLogin(login
                        -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureHandler(customLoginFailureHandler)
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout
                        -> logout
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login")
                ).rememberMe(rememberMe
                        -> rememberMe
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(60 * 60 * 24 * 31) //60(minute) * 60(hour) * 24 (day) * 31 (month)
                        .userDetailsService(userDetailsService)
                        .tokenRepository(tokenRepository())
                ).cors(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
