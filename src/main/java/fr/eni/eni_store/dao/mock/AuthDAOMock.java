package fr.eni.eni_store.dao.mock;

import fr.eni.eni_store.bo.AppUser;
import fr.eni.eni_store.dao.IDAOAuth;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("mock")
@Component
public class AuthDAOMock implements IDAOAuth {

    @Override
    public AppUser selectUserByEmailAndPassword(String email, String password) {

        // Faux user
        AppUser appUser = new AppUser();
        appUser.email = "test@gmail.com";
        appUser.password = "123456";

        // un role ROLE_USER mocké
        appUser.roles = Arrays.asList("ROLE_USER");

        // si pas bon
        if (!email.equals(appUser.email) || !password.equals(appUser.password)) {
            return null;
        }

        // Sinon on récupère le user
        return appUser;
    }

    @Override
    public AppUser selectUserByEmail(String email) {
        // Faux user
        AppUser appUser = new AppUser();
        appUser.email = "test@gmail.com";
        appUser.password = "123456";

        // un role ROLE_USER mocké
        appUser.roles = Arrays.asList("ROLE_USER");

        // si pas bon
        if (!email.equals(appUser.email)) {
            return null;
        }

        // Sinon on récupère le user
        return appUser;
    }
}
