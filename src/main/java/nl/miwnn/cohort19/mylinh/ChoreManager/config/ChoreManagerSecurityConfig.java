package nl.miwnn.cohort19.mylinh.ChoreManager.config;

import nl.miwnn.cohort19.mylinh.ChoreManager.service.ChoreManagerUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

/**
 * @author My Linh Lu
 */
@Configuration
@EnableWebSecurity
public class ChoreManagerSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(ChoreManagerSecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/",
                            "/chores",
                            "/chores/detail/**",
                            "/css/**",
                            "/webjars/**"
                    ).permitAll()
                    .requestMatchers(
                            "/chores/edit/**",
                            "/chores/add",
                            "/chores/delete"
                    ).hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .defaultSuccessUrl("/chores", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/chores")
                    .permitAll()
            );
    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        String password = UUID.randomUUID().toString();
//
//        log.info("==========================================================================");
//        log.info("Generated password: {}", password);
//        log.info("==========================================================================");
//
//        var gebruiker = User.builder()
//                .username("gebruiker")
//                .password(encoder.encode(password))
//                .roles("USER")
//                .build();
//        var admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("geheim123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(gebruiker, admin);
//    }
}
