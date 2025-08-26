package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.IDAOArticle;
import fr.eni.eni_store.dao.mock.ArticleDAOMock;
import fr.eni.eni_store.dao.DAOSaveResult;
import fr.eni.eni_store.locale.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.eni.eni_store.service.ServiceConstants.*;

@Service
public class ArticleService {

    private final IDAOArticle articleDAO;

    private final LocaleHelper localeHelper;

    public ArticleService(IDAOArticle articleDAO, LocaleHelper localeHelper) {
        this.articleDAO = articleDAO;
        this.localeHelper = localeHelper;
    }

    /**
     * Fonctionnalité pour récupérer tout les articles
     * @return 202 : Succès
     */
    public ServiceResponse<List<Article>> getAll(){
        // Cas 1
        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_GetAll_202"), articleDAO.selectAll());
    }

    public ServiceResponse<Article> getById(String id){
        // Essayer de récupérer l'article
        Article article = articleDAO.selectById(id);

        // Cas : Je trouve pas l'id
        if (article == null){
            return ServiceHelper.buildResponse(CD_ERR_NOT_FOUND, localeHelper.i18n("Service_Article_GetById_703"));
        }

        // Cas : Ok
        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_GetById_202"), article);
    }

    public ServiceResponse<Article> deleteById(String id){
        // Essayer de récupérer l'article
        boolean removeSuccess = articleDAO.deleteById(id);

        // Cas : Je trouve pas l'id/article non supprimé
        if (!removeSuccess){
            return ServiceHelper.buildResponse(CD_ERR_NOT_FOUND, localeHelper.i18n("Service_Article_Delete_703"));
        }

        // Cas : Ok
        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_Delete_202"));
    }

    public ServiceResponse<Article> save(Article article){

        // J'ai une seule DAO (qui fait create ou update selon l'id)
        DAOSaveResult<Article> daoSaveResult = articleDAO.save(article);

        if (daoSaveResult.isCreated){
            return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_Save_202"), daoSaveResult.data);
        }

        return ServiceHelper.buildResponse(CD_SUCCESS_PERSIST, localeHelper.i18n("Service_Article_Save_203"), daoSaveResult.data);
    }
}
