package com.expense.tracker.auth.config;

import com.expense.tracker.auth.eventProducer.UserInfoProducer;
import com.expense.tracker.auth.repository.UserRepository;
import com.expense.tracker.auth.service.UserDetailServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //this annotation means when app start at first this will execute
@EnableMethodSecurity //this will make sure this config use spring security
@Data
public class SecurityConfig { //this class direct all the calls needs to go through Auth
// Everything method in this class will provide bean singleton which spring will keep and use it for whole time

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServiceImpl userDetailServiceimpl;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    @Autowired
    private final RequestLoggingFilter requestLoggingFilter;


    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        //this will provide user info to spring at start that's why we implement UserDetail service there
        return new UserDetailServiceImpl(passwordEncoder, userRepository,userInfoProducer);
    }

 //Spring Security Filter Chain for all calls here we will define what happens with https(Secure) calls comes to this app

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https, JwtAuthFilter jwtAuthFilter) throws Exception{

        return https //Cross-Site Request Forgery(CSRF) Cross-Origin Resource Sharing (CORS) both security are disable for now
                .csrf(AbstractHttpConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/v1/login","/auth/v1/refreshToken","/auth/v1/signup").permitAll()//this req will be passed no auth needed
                        .anyRequest().authenticated() //except this all have to pass though auth
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//will not sore session DB
                .httpBasic(Customizer.withDefaults())//all other htt basic set to default
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)//Now add filter to all other calls before they hit. if we do not provide it goes to default.UsernamePasswordAuthenticationFilter is a type of jwtfilter
                .addFilterBefore(requestLoggingFilter,JwtAuthFilter.class)// for logging the http request
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { //this auth provider will help with user details
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailServiceimpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return  authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
