package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.AppUser;
import fr.eni.eni_store.dao.IDAOAuth;
import fr.eni.eni_store.service.dto.LoginRequest;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static fr.eni.eni_store.service.ServiceConstants.*;

@Service
public class AuthService {

    private final IDAOAuth daoAuth;

    public AuthService(IDAOAuth daoAuth) {
        this.daoAuth = daoAuth;
    }

    /**
     * Récupérer la valeur de app.jwt.secret dans application.properties
     */
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    /**
     * Récupérer la clé Key (binaire) depuis la clé secrète String
     * @return
     */
    private Key getSecretKey() {
        // convertir un string en base 64
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // convertir une base 64 en Key
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    /**
     * Fonctionnalité pour authentifier user en Stateless (token jwt)
     * @param loginRequest
     * @return
     */
    public ServiceResponse<String> auth(LoginRequest loginRequest) {
        // Je récupère le user en base
        AppUser loggedUser = daoAuth.selectUserByEmailAndPassword(loginRequest.email, loginRequest.password);

        // Tester que l'user existe en base
        if (loggedUser == null) {
            return ServiceHelper.buildResponse(CD_ERR_AUTH, "Couple email/mot de passe incorrect");
        }

        Date tokenLifetime = new Date(System.currentTimeMillis() + ((1000 * 60 * 60) * 1));
        //Date tokenLifetime = new Date(System.currentTimeMillis() + 1000);

        // Le code pour générer un token
        String token = Jwts.builder()
                .subject(loggedUser.email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(tokenLifetime)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        // Retourner succès
        return ServiceHelper.buildResponse(CD_SUCCESS_COMMON, "Authentifié(e) avec succès", token);
    }

    public ServiceResponse<Boolean> checkToken(String token){
        // Error: 1 - Si Empty
        if (token.isEmpty()){
            return ServiceHelper.buildResponse(CD_ERR_INVALID, "Token vide", false);
        }

        // Si token trop court (pas possible de substring 7 sinon crash)
        if (token.length() < 7){
            return ServiceHelper.buildResponse(CD_ERR_INVALID, "Token trop court", false);
        }

        // ATTENTION SELON LE CAS LE TOKEN EST SUFFIXE D'UN DISCRIMINANT
        // ex Bearer montoken
        // je dois ignorer les 7 premiers caractères (le mot Bearer avec l'espace)
        token = token.substring(7);

        try {
            // Outil pour récupérer le token (déchiffrer)
            JwtParser jwtParser = Jwts.parser().setSigningKey(getSecretKey()).build();

            // -- récupérer les claims de mon token (claims => toutes les info)
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();

            // Récupérer la date
            // 1: Version abstraite (couplage faible)
            // Function<Claims, Date> expirationFunction = Claims::getExpiration;
            // Date expirationDate = expirationFunction.apply(claims);
            // 2: Version explicite (couplage fort)
            Date expirationDate = claims.getExpiration();

            System.out.println(expirationDate);

        } catch (Exception e) {
            // Si la date d'expiration est depassé alors
            // Si exception jwt de type expiration
            if (e instanceof ExpiredJwtException){
                return ServiceHelper.buildResponse(CD_ERR_INVALID, "Token expiré", false);
            }

            // Si token malformé
            if (e instanceof MalformedJwtException){
                return ServiceHelper.buildResponse(CD_ERR_INVALID, "Token malformé", false);
            }

            return ServiceHelper.buildResponse(CD_ERR_INVALID, "Erreur inconnue", false);
        }

        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, "Token valide", true);
    }
}
