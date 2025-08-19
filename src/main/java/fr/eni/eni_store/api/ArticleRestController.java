package fr.eni.eni_store.api;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.service.ArticleService;
import fr.eni.eni_store.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    @Autowired
    ArticleService articleService;

    @GetMapping("all")
    public ServiceResponse<List<Article>> getAll(){

        return articleService.getAll();
    }

    @GetMapping("get/{id}")
    public ServiceResponse<Article> getById(@PathVariable Integer id){

        return articleService.getById(id);
    }

    @DeleteMapping("{id}")
    public ServiceResponse<Article> deleteById(@PathVariable Integer id){

        return articleService.deleteById(id);
    }

    @PostMapping("save")
    public ServiceResponse<Article> save(@RequestBody Article article){

        return articleService.save(article);
    }
}
