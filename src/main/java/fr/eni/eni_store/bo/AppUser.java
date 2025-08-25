package fr.eni.eni_store.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class AppUser {

    @Id
    public String id;

    public String email;
    public String password;
}
