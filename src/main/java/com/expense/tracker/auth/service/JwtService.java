package com.expense.tracker.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//how to use jwt token to get the user details
// extract all the claim and then with the claims function getsubject get the name
//to extract claims need to make sign key with secret key encoded with base 64 and apsse though hash256` to encode the sign key

@Service
@AllArgsConstructor
@Data
public class JwtService {

    // 256bit randomly generated key from internet
    // generally should be stored in a file somewhere outside
    public static final String SECRET= "ff33819ea487ca2735a06fbd54f2be4c6261d15cd73e18efbe08a0fb46e58cb6";


    //in Jwt everything comes in a claim object and we extract from that
    //this will extract name
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject); //here we have pass a function so we need to pass function as argument
    }


    //this will extract the expiration date
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration); //here we have pass a function so we need to pass function as argument
    }


    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());//this will check with current date
    }

    //from db will take user and from token we will check this both
    public boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //Function<Claims,T> which will take claim and return T type
    // because of generic now we can call again and again to get different result
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims=extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    // to extract all claims we need to get jwts object and use key to extract the claims
    private Claims extractAllClaim(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // here we are decoding our key with the base 64 decoder
    // creating sign key with one secret stored
    private SecretKey getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return  Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    //create token
    public String createToken(Map<String,Object> claim,String username){
        return Jwts
                .builder()
                .claims(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*1))
                .signWith(getSignKey())
                .compact();
    }

}
