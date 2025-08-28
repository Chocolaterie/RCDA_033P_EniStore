package fr.eni.eni_store.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Article {

    @Id
    public String id;

    @NotEmpty(message = "Le titre doit être renseigné")
    public String title;

    public Article() {
    }

    public Article(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
