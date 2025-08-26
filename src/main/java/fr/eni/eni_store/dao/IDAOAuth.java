package fr.eni.eni_store.dao;

import fr.eni.eni_store.bo.AppUser;

public interface IDAOAuth {

    public AppUser selectUserByEmailAndPassword(String email, String password);
    public AppUser selectUserByEmail(String email);
}
