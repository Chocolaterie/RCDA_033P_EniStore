package fr.eni.eni_store.dao.mongo;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.DAOSaveResult;
import fr.eni.eni_store.dao.IDAOArticle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Component
public class ArticleDAOMongo implements IDAOArticle {

    private final ArticleMongoRepository repository;

    public ArticleDAOMongo(ArticleMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> selectAll() {
        return repository.findAll();
    }

    @Override
    public Article selectById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DAOSaveResult<Article> save(Article article) {
        // Result préparé
        DAOSaveResult<Article> result = new DAOSaveResult<>();

        // Par défaut on trouve aucun article
        Article foundArticle = null;

        // Si id pas nul on essayer de trouver un article existant
        if (article.id != null){
            foundArticle = repository.findById(article.id).orElse(null);
        }

        // -------------------------------------------------------------
        // Si article existant => Update
        if (foundArticle != null){

            // Executer la requete save
            article = repository.save(article);

            // Informer que la DAO à update et non créee
            result.isCreated = false;
            // la data c'est l'article mise à jour
            result.data = article;

            return result;
        }

        // -------------------------------------------------------------
        // Sinon creation

        // Executer la requete save
        article = repository.save(article);

        // Informer que la DAO fait une création
        result.isCreated = true;
        // la data c'est l'article crée
        result.data = article;

        return result;
    }

    @Override
    public boolean deleteById(String id) {
        // Trouver un article existant
        Optional<Article> article = repository.findById(id);

        // Si existe pas => false
        if (article.isEmpty()){
            return false;
        }

        // Sinon supprimer l'article et true
        repository.delete(article.get());

        return true;
    }
}
