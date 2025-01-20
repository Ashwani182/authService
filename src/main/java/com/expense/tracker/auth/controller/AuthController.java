package com.expense.tracker.auth.controller;

import com.expense.tracker.auth.Util.UserUtility;
import com.expense.tracker.auth.entities.RefreshToken;
import com.expense.tracker.auth.model.UserDto;
import com.expense.tracker.auth.response.JwtResponseDto;
import com.expense.tracker.auth.service.JwtService;
import com.expense.tracker.auth.service.RefreshTokenService;
import com.expense.tracker.auth.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserDto userDto){
        try{
//            if(!UserUtility.validatePassword(userDto.getPassword())){
//                return new ResponseEntity<>("Password is not strong ..!", HttpStatus.BAD_REQUEST);
//            }
            Boolean isSignuped = userDetailService.signUpUser(userDto);
            if(!isSignuped){
                return new ResponseEntity<>("User already exist ..!", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getUsername());
            String jwtToken=jwtService.generateToken(userDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
