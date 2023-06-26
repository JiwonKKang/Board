package com.immersion.board.service;

import com.immersion.board.domain.Article;
import com.immersion.board.domain.type.SearchType;
import com.immersion.board.dto.ArticleDto;
import com.immersion.board.dto.ArticleUpdateDto;
import com.immersion.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;

    @Mock private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글을 검색하면, 게시글 리스트 반환")
    public void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() throws Exception {

        //given

        //when
        List<ArticleDto> articles =  sut.searchArticles(SearchType.TITLE, "search");
        //then
        assertThat(articles).isNotNull();
    }

    @Test
    @DisplayName("게시글을 조회하면, 게시글 반환")
    public void givenArticleId_whenSearchingArticle_thenReturnsArticle() throws Exception {

        //given

        //when
        ArticleDto article =  sut.searchArticle(1L);
        //then
        assertThat(article).isNotNull();
    }

    @Test
    @DisplayName("게시글을 입력하면 게시글을 저장한다")
    public void givenArticleInfo_whenSavingArticle_thenSavesArticle() throws Exception {

        //given
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //when
        sut.saveArticle(ArticleDto.of("title", "content", "hashtag", LocalDateTime.now(), "Jiwon"));

        //then
        then(articleRepository).should().save(any(Article.class));
    }

//    @Test
//    @DisplayName("게시글을 수정하면, 게시글을 업데이트한다")
//    public void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() throws Exception {
//
//        //given
//        given(articleRepository.save(any(Article.class))).willReturn(null);
//        //when
//        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "hashtag"));
//        //then
//        then(articleRepository).should().save(any(Article.class));
//    }
//
//    @Test
//    @DisplayName("게시글을 삭제하면, 게시글을 삭제한다")
//    public void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() throws Exception {
//
//        //given
//        willDoNothing().given(articleRepository).delete(any(Article.class));
//        //when
//        sut.deleteArticle(1L);
//        //then
//        then(articleRepository).should().delete(any(Article.class));
//    }
}