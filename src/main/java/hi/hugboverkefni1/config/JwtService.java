package hi.hugboverkefni1.config;

import hi.hugboverkefni1.persistence.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService{
    private static final String SECRET_KEY = "astringsecretatleast256bitslongwhatashitcourse";

    public String extractUsername(String jwtToken) {
        return getClaims(jwtToken).getSubject();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*3)) // 60s*60m*3h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean isValidToken(String jwtToken) {
        try{
            getClaims(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody();
    }



}
