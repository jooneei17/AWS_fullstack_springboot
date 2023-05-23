package com.jmymble.hello.security;

import com.jmymble.hello.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final String SECRET_KEY = "1234";

    public String create(UserEntity userEntity) {
        //기한은 지금으로부터 1일로 설정
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        //JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setExpiration(expiryDate)
                .compact();

    }

    //token id 반환
    public String validateAndGetUserId(String token) {
        //parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱
        //즉 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후 token의 서명과 비교
        //위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외 날림
        //그 중 우리는 userId가 필요하므로 getBody 부름
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}