package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

     private final String secret = "0123456789012345678901234567890123456789";


     public String genererToken(String email){

          SecretKey key = Keys.hmacShaKeyFor(
                  secret.getBytes()
          );


          return Jwts.builder()
                  .subject(email)
                  .issuedAt(new Date())
                  .expiration(
                          new Date(
                                  System.currentTimeMillis()
                                          + 86400000
                          )
                  )
                  .signWith(key)
                  .compact();

     }



     public String extraireEmail(String token){

          SecretKey key = Keys.hmacShaKeyFor(
                  secret.getBytes()
          );


          return Jwts.parser()
                  .verifyWith(key)
                  .build()
                  .parseSignedClaims(token)
                  .getPayload()
                  .getSubject();

     }
}
