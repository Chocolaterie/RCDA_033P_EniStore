package fr.eni.eni_store.api;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.service.ArticleService;
import fr.eni.eni_store.service.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(summary = "Endpoint pour récupérer tout les articles")
    @GetMapping("all")
    public ServiceResponse<List<Article>> getAll(){

        return articleService.getAll();
    }

    @Operation(summary = "Endpoint pour récupérer un article")
    @GetMapping("get/{id}")
    public ServiceResponse<Article> getById(@PathVariable String id){

        return articleService.getById(id);
    }

    @Operation(summary = "Endpoint pour supprimer un article")
    @DeleteMapping("{id}")
    public ServiceResponse<Article> deleteById(@PathVariable String id){

        return articleService.deleteById(id);
    }

    @Operation(summary = "Endpoint pour enregistrer un article",
            description = "Attention si l'article n'existe pas c'est une création. Sinon une mise à jour")
    @PostMapping("save")
    public ServiceResponse<Article> save(@RequestBody Article article){

        return articleService.save(article);
    }
}
