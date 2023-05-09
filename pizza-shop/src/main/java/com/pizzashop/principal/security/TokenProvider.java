package com.pizzashop.principal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements Serializable {
    @Value("${jwt.token.validity.access}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.token.validity.refresh}")
    private long REFRESH_TOKEN_VALIDITY;
    @Value("${jwt.signing.key}")
    private String SIGNING_KEY;

    @Value("${jwt.payload.key}")
    private String PAYLOAD_KEY;

    @Value("${jwt.authorities.key}")
    private String AUTHORITIES_KEY;

    /**
     * Get the User from the token.
     *
     * @param token String token.
     * @return String username from the token clams.
     */
    public String getUsernameFromToken(final String token) {
        log.info("Executing getUsernameFromToken from TokenProvider");

        return decode(getClaimFromToken(token, Claims::getSubject));
    }

    /**
     * Get expiration date from the token.
     *
     * @param token String token.
     * @return Date expiration date.
     */
    private Date getExpirationDateFromToken(final String token) {
        log.info("Executing getExpirationDateFromToken from TokenProvider");

        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get claim from token.
     *
     * @param token          String token.
     * @param claimsResolver Claim resolver.
     * @param <T>            Any object
     * @return Any object.
     */
    private <T> T getClaimFromToken(final String token,
                                    final Function<Claims, T> claimsResolver) {
        log.info("Executing getClaimFromToken from TokenProvider");

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Get all claims from token.
     *
     * @param token String token.
     * @return Claims from the token.
     */
    private Claims getAllClaimsFromToken(final String token) {
        log.info("Executing getAllClaimsFromToken from TokenProvider");

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Is the token has expired.
     *
     * @param token String token.
     * @return Boolean is the token has expired or not.
     */
    private Boolean isTokenExpired(final String token) {
        log.info("Executing isTokenExpired from TokenProvider");

        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    /**
     * Generate access token.
     *
     * @param authentication Authentication
     * @return String token.
     */
    public String generateAccessToken(final Authentication authentication) {
        log.info("Executing generateAccessToken from TokenProvider");

        return generateToken(authentication, ACCESS_TOKEN_VALIDITY);
    }

    /**
     * Generate refresh token.
     *
     * @param authentication Authentication
     * @return String token.
     */
    public String generateRefreshToken(final Authentication authentication) {
        log.info("Executing generateRefreshToken from TokenProvider");

        return generateToken(authentication, REFRESH_TOKEN_VALIDITY);
    }

    /**
     * Generate token.
     *
     * @param authentication Authentication
     * @param expiration     Long expiration time.
     * @return String token.
     */
    private String generateToken(final Authentication authentication,
                                 final long expiration) {
        log.info("Executing generateToken from TokenProvider");

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(encode(authentication.getName()))
                .claim(encode(AUTHORITIES_KEY), encode(authorities))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract the username from the token and compare it
     * to the User from the UserDetails.
     *
     * @param token       String token.
     * @param userDetails User Details.
     * @return Boolean.
     */
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        log.info("Executing validateToken from TokenProvider");

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extract the authorities from the token and create a new
     * UsernamePasswordAuthenticationToken with the UserDetails and those authorities.
     *
     * @param token       String token.
     * @param userDetails UserDetails.
     * @return UsernamePasswordAuthenticationToken.
     */
    public UsernamePasswordAuthenticationToken getAuthenticationToken
    (final String token, final UserDetails userDetails) {
        log.info("Executing getAuthenticationToken from TokenProvider");

        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(decode(claims.get(encode(AUTHORITIES_KEY)).toString()).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * Get the encode Key
     *
     * @return Key.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SIGNING_KEY));
    }

    /**
     * Crete a encode Key.
     *
     * @return String key.
     */
    private String creteStringSecretKey() {
        final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Encoders.BASE64.encode(key.getEncoded());
    }

    /**
     * Encode the string value.
     *
     * @param value String to encode.
     * @return String Encoded.
     */
    private String encode(final String value) {
        String toEncode = value + PAYLOAD_KEY;

        return Encoders.BASE64.encode(toEncode.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decode the string value.
     *
     * @param value String to decode.
     * @return String decoded.
     */
    private String decode(final String value) {
        return new String(Decoders.BASE64.decode(value))
                .replace(PAYLOAD_KEY, "");
    }
}
