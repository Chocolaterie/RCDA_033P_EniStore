package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    public ServiceResponse<List<Article>> getAll(){
        // Cas 1
        return ServiceHelper.buildResponse("202", articleDAO.selectAll());
    }

    public ServiceResponse<Article> getById(int id){
        // Essayer de récupérer l'article
        Article article = articleDAO.selectById(id);

        // Cas : Je trouve pas l'id
        if (article == null){
            return ServiceHelper.buildResponse("703");
        }

        // Cas : Ok
        return ServiceHelper.buildResponse("202", article);
    }

    public ServiceResponse<Article> deleteById(int id){
        // Essayer de récupérer l'article
        boolean removeSuccess = articleDAO.deleteById(id);

        // Cas : Je trouve pas l'id/article non supprimé
        if (!removeSuccess){
            return ServiceHelper.buildResponse("703");
        }

        // Cas : Ok
        return ServiceHelper.buildResponse("202");
    }

    public ServiceResponse<Article> save(Article article){
        // TODO
        // J'ai deux DAO (Une pour create/update)

        // ON FAIT: J'ai une seule DAO (qui fait create ou update selon l'id)
        Article savedArticle = articleDAO.save(article);

        return ServiceHelper.buildResponse("202", savedArticle);
    }
}
