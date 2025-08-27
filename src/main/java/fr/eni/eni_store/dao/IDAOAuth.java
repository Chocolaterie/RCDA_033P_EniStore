package fr.eni.eni_store.dao;

import fr.eni.eni_store.bo.AppUser;

/**
 * Interface de définition pour les opérations d’authentification
 * sur les utilisateurs de l’application.
 */
public interface IDAOAuth {

    /**
     * Recherche un utilisateur dans la base de données en fonction
     * de son adresse e-mail et de son mot de passe.
     *
     * @param email    l’adresse e-mail de l’utilisateur
     * @param password le mot de passe de l’utilisateur
     * @return l’objet AppUser correspondant si trouvé, sinon null
     */
    public AppUser selectUserByEmailAndPassword(String email, String password);

    /**
     * Recherche un utilisateur dans la base de données en fonction
     * uniquement de son adresse e-mail.
     *
     * @param email l’adresse e-mail de l’utilisateur
     * @return l’objet AppUser correspondant si trouvé, sinon null
     */
    public AppUser selectUserByEmail(String email);
}
