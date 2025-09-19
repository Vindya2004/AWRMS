package org.example.awrms;

import org.example.awrms.dto.ArticleDTO;
import org.example.awrms.entity.Article;
import org.example.awrms.repository.ArticleRepository;
import org.example.awrms.service.impl.ArticleServiceImpl;
import org.example.awrms.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTests {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ModelMapper modelMapper;

    private ArticleDTO articleDTO;
    private Article article;

    @BeforeEach
    void setUp() {
        articleDTO = new ArticleDTO(1L, "Test Title", "Test Description", "test.png");
        article = new Article(1L, "Test Title", "Test Description", "test.png");
    }

    @Test
    void shouldSaveArticle() {
        // arrange
        when(articleRepository.existsByTitle(articleDTO.getTitle())).thenReturn(false);
        when(modelMapper.map(articleDTO, Article.class)).thenReturn(article);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // act
        int result = articleService.saveArticle(articleDTO);

        // assert
        assertEquals(VarList.Created, result);
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void shouldNotSaveDuplicateArticle() {
        // arrange
        when(articleRepository.existsByTitle(articleDTO.getTitle())).thenReturn(true);

        // act
        int result = articleService.saveArticle(articleDTO);

        // assert
        assertEquals(VarList.Not_Acceptable, result);
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void shouldUpdateArticle() {
        // arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        ArticleDTO updatedDTO = new ArticleDTO(1L, "Updated Title", "Updated Description", "new.png");

        // act
        int result = articleService.updateArticle(1L, updatedDTO);

        // assert
        assertEquals(VarList.Created, result);
        assertEquals("Updated Title", article.getTitle());
        assertEquals("Updated Description", article.getDescription());
        assertEquals("new.png", article.getImageUrl());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingArticle() {
        // arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        // act
        int result = articleService.updateArticle(1L, articleDTO);

        // assert
        assertEquals(VarList.Not_Found, result);
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void shouldDeleteArticle() {
        // arrange
        when(articleRepository.existsById(1L)).thenReturn(true);

        // act
        int result = articleService.deleteArticle(1L);

        // assert
        assertEquals(VarList.Created, result);
        verify(articleRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingArticle() {
        // arrange
        when(articleRepository.existsById(1L)).thenReturn(false);

        // act
        int result = articleService.deleteArticle(1L);

        // assert
        assertEquals(VarList.Not_Found, result);
        verify(articleRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldReturnAllArticles() {
        // arrange
        Article article2 = new Article(2L, "Another Title", "Another Description", "another.png");
        List<Article> articles = List.of(article, article2);

        when(articleRepository.findAll()).thenReturn(articles);
        when(modelMapper.map(article, ArticleDTO.class)).thenReturn(articleDTO);
        when(modelMapper.map(article2, ArticleDTO.class))
                .thenReturn(new ArticleDTO(2L, "Another Title", "Another Description", "another.png"));

        // act
        List<ArticleDTO> result = articleService.getAllArticles();

        // assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleRepository, times(1)).findAll();
    }
}
