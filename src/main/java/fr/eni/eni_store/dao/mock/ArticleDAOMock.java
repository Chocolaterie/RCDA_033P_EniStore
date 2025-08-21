package fr.eni.eni_store.dao.mock;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.DAOSaveResult;
import fr.eni.eni_store.dao.IDAOArticle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("mock")
@Component
public class ArticleDAOMock implements IDAOArticle {

    List<Article> DB_Articles;

    public ArticleDAOMock(){
        DB_Articles = new ArrayList<>();

        for (int i = 0; i < 3; i++){

            Article p = new Article(Integer.toString(i+1), String.format("Article %d", i));

            DB_Articles.add(p);
        }
    }

    public List<Article> selectAll(){
        return DB_Articles;
    }

    public Article selectById(String id){

        Optional<Article> foundArticle = DB_Articles.stream().filter(article -> article.id == id).findFirst();

        return foundArticle.orElse(null);
    }

    public boolean deleteById(String id){

        return DB_Articles.removeIf(article -> article.id == id);
    }

    public DAOSaveResult<Article> save(Article article){

        DAOSaveResult<Article> result = new DAOSaveResult<>();

        // sinon mettre à jour
        // -- Si article existe ne base alors le modifier
        Optional<Article> foundArticle = DB_Articles.stream().filter(value -> value.id == article.id).findFirst();

        // si existe -> alors je le modifie
        if (foundArticle.isPresent()){
            foundArticle.get().title = article.title;

            result.isCreated = false;
            result.data = foundArticle.get();

            return result;
        }

        // Si id == null
        // générer un faux id
        article.id = Integer.toString(DB_Articles.size() + 100);
        // Je ajoute l'article dans le tableau
        DB_Articles.add(article);

        result.isCreated = true;
        result.data = article;

        return result;
    }
}
