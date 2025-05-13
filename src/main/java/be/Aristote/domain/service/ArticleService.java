package be.Aristote.domain.service;

import be.Aristote.api.dto.ArticleDTO;
import be.Aristote.domain.repository.ArticleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

        private final ArticleRepository articleRepository;

        public ArticleService(ArticleRepository articleRepository) {
                this.articleRepository = articleRepository;
        }

        public ArticleDTO retrieveBy(String id) {
                Long articleId = Long.parseLong(id);

                return articleRepository.findById(articleId)
                                .map(article -> new ArticleDTO(
                                                article.getTitle(),
                                                article.getIsbn(),
                                                article.getDescription(),
                                                article.getImageUrl(),
                                                article.getPrice(),
                                                article.getQuantity()))
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Aucun article trouvé pour l'id : " + id));
        }

        public List<ArticleDTO> retrieveAll() {
                return articleRepository.findAll().stream()
                                .map(article -> new ArticleDTO(
                                                article.getTitle(),
                                                article.getIsbn(),
                                                article.getDescription(),
                                                article.getImageUrl(),
                                                article.getPrice(),
                                                article.getQuantity()))
                                .toList();
        }
}