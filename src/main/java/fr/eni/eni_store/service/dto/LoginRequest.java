package fr.eni.eni_store.service.dto;

/**
 * Classe qui sert de DTO/Pojo pour contenir les identifiants d'authentification
 */
public class LoginRequest {

    public String email;
    public String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
