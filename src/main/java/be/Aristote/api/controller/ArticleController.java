package be.Aristote.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.Aristote.api.dto.ArticleDTO;
import be.Aristote.domain.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value ="/all", produces = "application/json")
    public List<ArticleDTO> getAllArticles() throws IllegalAccessException {
        return articleService.retrieveAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ArticleDTO getArticle(@PathVariable String id) throws IllegalAccessException {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new IllegalAccessException("No id provided");
        }
        if (!id.matches("\\d+")) {
            throw new IllegalAccessException("Invalid id");
        }

        return articleService.retrieveBy(id);
    }
}
