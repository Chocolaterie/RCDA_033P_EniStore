package fr.eni.eni_store.dao.mock;

import fr.eni.eni_store.bo.AppUser;
import fr.eni.eni_store.dao.IDAOAuth;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("mock")
@Component
public class AuthDAOMock implements IDAOAuth {

    List<AppUser> DB_USERS;

    public AuthDAOMock(){
        DB_USERS = new ArrayList<>();

        // Faux user 1
        AppUser appUser = new AppUser();
        appUser.email = "test@gmail.com";
        appUser.password = "123456";

        // un role ROLE_USER mocké
        appUser.roles = Arrays.asList("ROLE_USER");

        DB_USERS.add(appUser);

        // Faux admin 1
        AppUser appUserAdmin = new AppUser();
        appUserAdmin.email = "admin@gmail.com";
        appUserAdmin.password = "123456";

        // un role ROLE_USER et ROLE_ADMIN mocké
        appUserAdmin.roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        DB_USERS.add(appUserAdmin);
    }

    @Override
    public AppUser selectUserByEmailAndPassword(String email, String password) {

        // Chercher un user par son email et mot de passe
        AppUser foundUser = DB_USERS.stream().filter(user -> user.email.equals(email) && user.password.equals(password)).findFirst().orElse(null);

        return foundUser;
    }

    @Override
    public AppUser selectUserByEmail(String email) {

        // Chercher un user par son email et mot de passe
        AppUser foundUser = DB_USERS.stream().filter(user -> user.email.equals(email)).findFirst().orElse(null);

        return foundUser;
    }
}
