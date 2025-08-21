package fr.eni.eni_store;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mock")
@SpringBootTest
class EniStoreApplicationTests {

    @Autowired
    ArticleService articleService;

	@Test
	void contextLoads() {
	}

    @Test
    void GetAll_Test() {
        assertThat(articleService.getAll().code).isEqualTo("202");
    }

    @Test
    void GetById_Test() {
        // Cas 1 - 703
        assertThat(articleService.getById("1586").code).isEqualTo("703");

        // Cas 2 - 202
        assertThat(articleService.getById("1").code).isEqualTo("202");
    }

    @Test
    void Delete_Test() {
        // Cas 1 - 703
        assertThat(articleService.deleteById("1586").code).isEqualTo("703");

        // Cas 2 - 202
        assertThat(articleService.deleteById("1").code).isEqualTo("202");
    }

    @Test
    void Save_Test() {
        // Cas 1 - 202 (Creation)
        Article newArticle = new Article("1596", "Titre test");
        assertThat(articleService.save(newArticle).code).isEqualTo("202");

        // Cas 2 - 203 (Article existant à modifier)
        Article foundArticle = articleService.getById("1").data;
        foundArticle.title = "Nouveau titre";

        assertThat(articleService.save(foundArticle).code).isEqualTo("203");
    }

}
