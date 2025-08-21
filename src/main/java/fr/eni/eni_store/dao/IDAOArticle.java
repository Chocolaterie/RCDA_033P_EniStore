package fr.eni.eni_store.dao;

import fr.eni.eni_store.bo.Article;

import java.util.List;

public interface IDAOArticle {

    List<Article> selectAll();

    Article selectById(String id);

    DAOSaveResult<Article> save(Article article);

    boolean deleteById(String id);
}
