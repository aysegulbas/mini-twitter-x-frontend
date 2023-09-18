package com.workintech.twitterApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {//token generate edicez
    private JwtEncoder jwtEncoder;
@Autowired
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public String generateJwtToken(Authentication authentication){//userın autentice bilgisini tutar
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();//şu anın zamanını alır
        JwtClaimsSet claims = JwtClaimsSet.builder()//token'ın içinde tutacaklarımız
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(6,ChronoUnit.DAYS))
                .subject(authentication.getName())//aslında username'i döner, ama biz username'i email olarak belirledik
                .claim("roles", roles)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();//Yani biz tokenın içinde neler olacağını ve ne şekilde encode edileceğini biz belirledik
    }
}
