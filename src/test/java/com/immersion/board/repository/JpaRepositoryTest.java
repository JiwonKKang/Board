package com.immersion.board.repository;

import com.immersion.board.config.JpaConfig;
import com.immersion.board.domain.Article;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("JPA 연결 테스트")
class JpaRepositoryTest {


    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;


    @Autowired
    public JpaRepositoryTest(ArticleRepository articleRepository, ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("select Test")
    public void givenTestData_whenSelecting_thenWorkFine() throws Exception {

        //given

        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @Test
    @DisplayName("insert Test")
    public void givenTestData_whenInserting_thenWorkFine() throws Exception {

        //given
        long count = articleRepository.count();


        //when
        Article savedArticle = articleRepository.save(Article.of("new aricle", "new content", "#spring"));

        //then
        assertThat(articleRepository.count())
                .isEqualTo(count + 1);
    }

    @Test
    @DisplayName("update Test")
    public void givenTestData_whenUpdating_thenWorkFine() throws Exception {

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#Springboot";
        article.setHashtag(updateHashtag);

        articleRepository.flush();

        //when


        //then
        assertThat(articleRepository.findById(1L).orElseThrow()).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }
}