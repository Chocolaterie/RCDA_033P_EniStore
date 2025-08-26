package fr.eni.eni_store.dao.mongo;

import fr.eni.eni_store.bo.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleMongoRepository extends MongoRepository<Article, String> {

}