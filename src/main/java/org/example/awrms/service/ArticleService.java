package org.example.awrms.service;

import org.example.awrms.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    int updateArticle(Long id, ArticleDTO articleDTO);

    int saveArticle(ArticleDTO articleDTO);

    int deleteArticle(Long id);

    List<ArticleDTO> getAllArticles();
}
