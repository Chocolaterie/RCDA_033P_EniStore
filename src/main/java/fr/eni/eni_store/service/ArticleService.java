package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.dao.DAOSaveResult;
import fr.eni.eni_store.dao.IDAOArticle;
import fr.eni.eni_store.locale.LocaleHelper;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.eni.eni_store.service.ServiceConstants.*;

/**
 * Service applicatif pour la gestion des {@link Article}.
 * Centralise la logique métier et renvoie des {@link ServiceResponse}
 * avec codes et messages localisés.
 */
@Service
public class ArticleService {

    private final IDAOArticle articleDAO;

    private final LocaleHelper localeHelper;

    public ArticleService(IDAOArticle articleDAO, LocaleHelper localeHelper) {
        this.articleDAO = articleDAO;
        this.localeHelper = localeHelper;
    }

    /**
     * Récupère l’ensemble des articles.
     *
     * @return
     * <ul>
     *   <li><b>202 (CD_SUCCESS_DEFAULT)</b> : succès, liste potentiellement vide mais requête traitée.</li>
     * </ul>
     */
    public ServiceResponse<List<Article>> getAll(){
        // Cas 1
        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_GetAll_202"), articleDAO.selectAll());
    }

    /**
     * Récupère un article par son identifiant.
     *
     * @param id identifiant unique de l’article
     * @return
     * <ul>
     *   <li><b>703 (CD_ERR_NOT_FOUND)</b> : article introuvable pour l’id fourni.</li>
     *   <li><b>202 (CD_SUCCESS_DEFAULT)</b> : succès, article trouvé et retourné.</li>
     * </ul>
     */
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

    /**
     * Supprime un article par son identifiant.
     *
     * @param id identifiant unique de l’article
     * @return
     * <ul>
     *   <li><b>703 (CD_ERR_NOT_FOUND)</b> : article introuvable ou non supprimé.</li>
     *   <li><b>202 (CD_SUCCESS_DEFAULT)</b> : succès, article supprimé.</li>
     * </ul>
     */
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

    /**
     * Crée ou met à jour un article.
     * L’implémentation DAO décide création vs mise à jour selon la présence/valeur de l’identifiant.
     *
     * @param article article à créer ou à mettre à jour
     * @return
     * <ul>
     *   <li><b>202 (CD_SUCCESS_DEFAULT)</b> : succès, article créé.</li>
     *   <li><b>203 (CD_SUCCESS_PERSIST)</b> : succès, article mis à jour (persisté).</li>
     * </ul>
     */
    public ServiceResponse<Article> save(Article article){

        // J'ai une seule DAO (qui fait create ou update selon l'id)
        DAOSaveResult<Article> daoSaveResult = articleDAO.save(article);

        if (daoSaveResult.isCreated){
            return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, localeHelper.i18n("Service_Article_Save_202"), daoSaveResult.data);
        }

        return ServiceHelper.buildResponse(CD_SUCCESS_PERSIST, localeHelper.i18n("Service_Article_Save_203"), daoSaveResult.data);
    }
}
