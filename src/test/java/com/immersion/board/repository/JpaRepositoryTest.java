package com.immersion.board.repository;

import com.immersion.board.config.JpaConfig;
import com.immersion.board.domain.Article;
import com.immersion.board.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DisplayName("JPA 연결 테스트")
class JpaRepositoryTest {


    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;


    @Autowired
    public JpaRepositoryTest(ArticleRepository articleRepository,
                             ArticleCommentRepository articleCommentRepository,
                             UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
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
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));


        //when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring", userAccount));

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

    @Test
    @DisplayName("delete Test")
    public void givenTestData_whenDeleting_thenWorkFine() throws Exception {

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long preArticleCount = articleRepository.count();
        long preArticleCommentCount = articleCommentRepository.count();
        int commentSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(preArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(preArticleCommentCount-commentSize);
    }

    @TestConfiguration
    @EnableJpaAuditing
    public static class TestJpaConfig {

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("uno");
        }
    }
}
