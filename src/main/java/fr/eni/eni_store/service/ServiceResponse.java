package fr.eni.eni_store.service;

/**
 * Classe générique représentant une réponse de service,
 * généralement utilisée pour communiquer le résultat d’une
 * opération (succès, erreur, message, etc.) aux couches supérieures.
 *
 * @param <T> le type de données renvoyées dans la réponse
 */
public class ServiceResponse<T> {


    /**
     * Code de statut représentant le résultat de l’opération
     * (par exemple : "202" pour succès, "704" pour non trouvé, etc.).
     */
    public String code;

    /**
     * Message descriptif associé au résultat de l’opération
     * (par exemple : "Opération réussie", "Utilisateur introuvable", etc.).
     */
    public String message;

    /**
     * Données renvoyées par le service, si disponibles.
     */
    public T data;
}