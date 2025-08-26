package fr.eni.eni_store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eni.eni_store.service.AuthService;
import fr.eni.eni_store.service.ServiceResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static fr.eni.eni_store.service.ServiceConstants.CD_SUCCESS_DEFAULT;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AuthService authService;

    public JwtAuthFilter(ObjectMapper objectMapper, AuthService authService) {
        this.objectMapper = objectMapper;
        this.authService = authService;
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
        }

        // passer (Oui/Ok)
        filterChain.doFilter(request, response);
    }
}