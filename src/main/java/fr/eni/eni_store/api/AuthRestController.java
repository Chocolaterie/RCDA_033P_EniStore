package fr.eni.eni_store.api;

import fr.eni.eni_store.service.AuthService;
import fr.eni.eni_store.service.ServiceResponse;
import fr.eni.eni_store.service.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("api/auth")
    public ServiceResponse<String> auth(@RequestBody LoginRequest loginRequest){
        return authService.auth(loginRequest);
    }
}
