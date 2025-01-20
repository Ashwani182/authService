package com.expense.tracker.auth.service;

import com.expense.tracker.auth.entities.UserInfo;
import com.expense.tracker.auth.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//this custom user detail is used for the UserInfo in spring security so it uses collection of GrantedAuthority
// this collection of GrantedAuthority will hold all the roles for the user and this constructor will help to get that
public class CustomUserDetails extends UserInfo implements UserDetails {

    private String userName;

    private  String password;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo userInfo){
        userName = userInfo.getUsername();
        password = userInfo.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>(); // we are making a list of granted authority
        for(UserRole role :userInfo.getUserRoleSet()){
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities=auths;
    }

    @Override
   public String getPassword(){
        return this.password;
   }

   @Override
    public String getUsername(){
        return this.userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
