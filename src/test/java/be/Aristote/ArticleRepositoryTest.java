package be.Aristote;

import be.Aristote.domain.model.ArticleEntity;
import be.Aristote.domain.model.CategoryEntity;
import be.Aristote.domain.repository.ArticleRepository;
import be.Aristote.domain.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateAndFindArticle() {
        // Création d’une catégorie d’abord
        CategoryEntity category = new CategoryEntity();
        category.setName("Bande dessinée");
        CategoryEntity savedCategory = categoryRepository.save(category);

        // Création d’un article
        ArticleEntity article = new ArticleEntity();
        article.setTitle("Tintin au Congo");
        article.setDescription("BD de Hergé");
        article.setPrice(9.99);
        article.setQuantity(3);
        article.setCategory(savedCategory); // Liaison obligatoire

        // Sauvegarde dans la base
        ArticleEntity saved = articleRepository.save(article);

        // Récupération
        Optional<ArticleEntity> found = articleRepository.findById(saved.getId());

        // Vérifications
        assertTrue(found.isPresent());
        assertEquals("Tintin au Congo", found.get().getTitle());
        assertEquals(3, found.get().getQuantity());
        assertEquals("Bande dessinée", found.get().getCategory().getName());
    }
}