package fr.eni.eni_store.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Article {

    @Id
    public String id;

    public String title;

    public Article() {
    }

    public Article(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
