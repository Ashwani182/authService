package com.expense.tracker.auth.service;

import com.expense.tracker.auth.entities.UserInfo;
import com.expense.tracker.auth.model.UserInfoDto;
import com.expense.tracker.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Component
public class UserDetailServiceImpl implements UserDetailsService { //UserDetailsService comes from spring security

    @Autowired
    private final PasswordEncoder passwordEncoder; //from spring to do encoding of password

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //login user
        UserInfo user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("could not found the user ...!!");
        }
        return new CustomUserDetails(user); //now UserDetails we will get from the customuserdetails
        //this implements UserDetails and extends the Userinfo

    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto){

            userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword())); //it encodes the password with hashkey
            if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
                return false;
            }else {
                UserInfo userInfo=UserInfo.builder()
                        .username(userInfoDto.getUsername())
                        .password(userInfoDto.getPassword())
                        .userid(UUID.randomUUID().toString())
                        .userRoleSet(new HashSet<>())
                        .build();
                userRepository.save(userInfo);
                // pushEventToQueue
                return true;
            }
    }

}
