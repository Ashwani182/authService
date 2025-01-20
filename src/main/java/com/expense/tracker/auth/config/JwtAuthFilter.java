package com.expense.tracker.auth.config;

import com.expense.tracker.auth.service.JwtService;
import com.expense.tracker.auth.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Data
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { //OncePerRequestFilter is applies to a request

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailServiceImpl userDetailService;

    // in Java Server runs under a servlet program and our code run on server
    // so first httpservlet request goes and then it goes to code becomes http request
    // so before reaching to our code we are applying the filter at server
    @Override //this filter is applied to servlet request so before reching to server (controller)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //first get header with Authorization anme
        String authHeader = request.getHeader("Authorization");
        String token= null;
        String userName=null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token =authHeader.substring(7);//Authorization start with Bearer<space> 7 characters after all token
            userName=jwtService.extractUsername(token);
        }

        if(userName !=null && SecurityContextHolder.getContext().getAuthentication() == null){ //if application starts then this will execute to store token in spring context
            UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            if(jwtService.validateToken(token,userDetails)){ //if token is valid set security context holder token with request
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

    }
}
