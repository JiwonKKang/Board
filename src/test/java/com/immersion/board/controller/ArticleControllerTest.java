package com.immersion.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("View 컨트롤러 = 게시글")
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {

        //given

        //when&then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));

    }

    @Test
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출")
    public void givenNothing_whenRequestingSearchView_thenReturnsSearchView() throws Exception {

        //given

        //when&then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));

    }

    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상호출")
    public void givenNothing_whenRequestingHashtagView_thenReturnsHashtagView() throws Exception {

        //given

        //when&then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));

    }
}