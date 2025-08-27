package fr.eni.eni_store.dao;

import fr.eni.eni_store.bo.Article;

import java.util.List;

/**
 * Interface de définition pour les opérations CRUD
 * liées aux articles dans l’application.
 */
public interface IDAOArticle {

    /**
     * Récupère la liste de tous les articles disponibles
     * dans la base de données.
     *
     * @return une liste d’objets Article
     */
    List<Article> selectAll();

    /**
     * Recherche un article spécifique en fonction de son identifiant.
     *
     * @param id l’identifiant unique de l’article
     * @return l’objet Article correspondant si trouvé, sinon null
     */
    Article selectById(String id);

    /**
     * Enregistre un nouvel article ou met à jour un article existant.
     *
     * @param article l’article à sauvegarder
     * @return un objet DAOSaveResult contenant l’article sauvegardé
     *         ainsi que le statut de l’opération
     */
    DAOSaveResult<Article> save(Article article);

    /**
     * Supprime un article de la base de données en fonction de son identifiant.
     *
     * @param id l’identifiant unique de l’article
     * @return true si la suppression a réussi, false sinon
     */
    boolean deleteById(String id);
}
