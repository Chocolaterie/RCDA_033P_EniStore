package fr.eni.eni_store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eni.eni_store.bo.AppUser;
import fr.eni.eni_store.dao.IDAOAuth;
import fr.eni.eni_store.service.AuthService;
import fr.eni.eni_store.service.ServiceResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static fr.eni.eni_store.service.ServiceConstants.CD_SUCCESS_DEFAULT;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AuthService authService;
    private final IDAOAuth daoAuth;

    public JwtAuthFilter(ObjectMapper objectMapper, AuthService authService, IDAOAuth daoAuth) {
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.daoAuth = daoAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Si l'url est different de /api/auth -> verifier le token
        String url = request.getRequestURI();

        if (!url.startsWith("/api/auth")) {
            // Récupérer le token
            String token = request.getHeader("Authorization");

            // Appeler notre service qui check le token
            ServiceResponse<Boolean> serviceResponse = authService.checkToken(token);

            // Si pas bon (!= 202 code métier)
            if (!serviceResponse.code.equals(CD_SUCCESS_DEFAULT)) {
                // Forcer la réponse http à être en JSON
                response.setContentType("application/json");

                objectMapper.writeValue(response.getWriter(), serviceResponse);
                return;
            }

            // TODO ???
            // Si spring secu n'as pas encore de user connecté dans son context
            // Attention dans mon cas, n'est pas connecté = anonyme pour spring
            Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
            if (securityContext.getPrincipal().equals("anonymousUser")) {
                // Récupérer un vrai USER (Service/DAO)
                // Récupérer par l'email  pourquoi ? Car le token contient que l'email de l'user
                // Option 3 (Lecture plus facile) : Un helper qui récupère le mail d'un token
                token = token.substring(7);
                String email = authService.getEmailFromToken(token);

                // A jour : Un user récupéré depuis la DAO (AppUser qui est un UserDetails)
                AppUser loggedUser = daoAuth.selectUserByEmail(email);

                // AVANT : (Un user instancié) : Comment retrouver/instancier mon AppUser qui est un UserDetails
                /*
                AppUser loggedUser = new AppUser();
                loggedUser.email = "kfd@gmail.com";
                loggedUser.password = "dfsdfsdfsdf";
                */

                // Encapsuler notre user dans un token spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        loggedUser.email, loggedUser.password, loggedUser.getAuthorities()
                );

                // Alors lui donner notre user custom en context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // passer (Oui/Ok)
        filterChain.doFilter(request, response);
    }
}