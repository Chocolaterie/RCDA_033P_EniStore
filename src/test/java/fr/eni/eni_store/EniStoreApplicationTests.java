package fr.eni.eni_store;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.service.ArticleService;
import fr.eni.eni_store.service.AuthService;
import fr.eni.eni_store.service.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static fr.eni.eni_store.service.ServiceConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mock")
@SpringBootTest
class EniStoreApplicationTests {

    @Autowired
    ArticleService articleService;

    @Autowired
    AuthService authService;

	@Test
	void contextLoads() {
	}

    @Test
    void GetAll_Test() {
        assertThat(articleService.getAll().code).isEqualTo(CD_SUCCESS_DEFAULT);
    }

    @Test
    void GetById_Test() {
        // Cas 1 - 703
        assertThat(articleService.getById("1586").code).isEqualTo(CD_ERR_NOT_FOUND);

        // Cas 2 - 202
        assertThat(articleService.getById("1").code).isEqualTo(CD_SUCCESS_DEFAULT);
    }

    @Test
    void Delete_Test() {
        // Cas 1 - 703
        assertThat(articleService.deleteById("1586").code).isEqualTo(CD_ERR_NOT_FOUND);

        // Cas 2 - 202
        assertThat(articleService.deleteById("1").code).isEqualTo(CD_SUCCESS_DEFAULT);
    }

    @Test
    void Save_Test() {
        // Cas 1 - 202 (Creation)
        Article newArticle = new Article("1596", "Titre test");
        assertThat(articleService.save(newArticle).code).isEqualTo(CD_SUCCESS_DEFAULT);

        // Cas 2 - 203 (Article existant à modifier)
        Article foundArticle = articleService.getById("1").data;
        foundArticle.title = "Nouveau titre";

        assertThat(articleService.save(foundArticle).code).isEqualTo(CD_SUCCESS_PERSIST);
    }

    @Test
    void Auth_Login_Test_200(){
        LoginRequest loginRequest = new LoginRequest("test@gmail.com", "123456");
        String code = authService.auth(loginRequest).code;

        assertThat(code).isEqualTo(CD_SUCCESS_COMMON);
    }

    @Test
    void Auth_Login_Test_765(){
        LoginRequest loginRequest = new LoginRequest("test1515@gmail.com", "1234568");
        String code = authService.auth(loginRequest).code;

        assertThat(code).isEqualTo(CD_ERR_AUTH);
    }

}
