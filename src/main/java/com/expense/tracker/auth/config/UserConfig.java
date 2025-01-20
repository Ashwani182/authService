package com.expense.tracker.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //this annotation means when app start at first this will execute
public class UserConfig { //in this we are making bean for password encoder we have used for user password

    @Bean
    public PasswordEncoder passwordEncoder(){ //this will create bean at start of app run and we have autowired the passwordencoder
        return new BCryptPasswordEncoder();
    }
}
