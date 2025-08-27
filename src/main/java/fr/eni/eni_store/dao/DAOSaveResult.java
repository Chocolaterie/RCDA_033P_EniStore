package fr.eni.eni_store.dao;

/**
 * Classe générique représentant le résultat d’une opération de sauvegarde
 * (création ou mise à jour) dans la base de données.
 *
 * @param <T> le type de l’objet sauvegardé
 */
public class DAOSaveResult<T> {

    /**
     * Indique si l’objet a été créé (true) ou mis à jour (false).
     */
    public boolean isCreated;


    /**
     * Contient l’objet sauvegardé après l’opération.
     */
    public T data;
}
