package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.ArticleDAO;
import fr.eni.eni_store.dao.DAOSaveResult;
import fr.eni.eni_store.locale.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private LocaleHelper localeHelper;

    public ServiceResponse<List<Article>> getAll(){
        // Cas 1
        return ServiceHelper.buildResponse("202", localeHelper.i18n("Service_Article_GetAll_202"), articleDAO.selectAll());
    }

    public ServiceResponse<Article> getById(int id){
        // Essayer de récupérer l'article
        Article article = articleDAO.selectById(id);

        // Cas : Je trouve pas l'id
        if (article == null){
            return ServiceHelper.buildResponse("703", localeHelper.i18n("Service_Article_GetById_703"));
        }

        // Cas : Ok
        return ServiceHelper.buildResponse("202", localeHelper.i18n("Service_Article_GetById_202"), article);
    }

    public ServiceResponse<Article> deleteById(int id){
        // Essayer de récupérer l'article
        boolean removeSuccess = articleDAO.deleteById(id);

        // Cas : Je trouve pas l'id/article non supprimé
        if (!removeSuccess){
            return ServiceHelper.buildResponse("703", localeHelper.i18n("Service_Article_Delete_703"));
        }

        // Cas : Ok
        return ServiceHelper.buildResponse("202", localeHelper.i18n("Service_Article_Delete_202"));
    }

    public ServiceResponse<Article> save(Article article){

        // J'ai une seule DAO (qui fait create ou update selon l'id)
        DAOSaveResult<Article> daoSaveResult = articleDAO.save(article);

        if (daoSaveResult.isCreated){
            return ServiceHelper.buildResponse("202", localeHelper.i18n("Service_Article_Save_202"), daoSaveResult.data);
        }

        return ServiceHelper.buildResponse("203", localeHelper.i18n("Service_Article_Save_203"), daoSaveResult.data);
    }
}
