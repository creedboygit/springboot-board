package com.creedboy.springbootboard.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.creedboy.springbootboard.config.SecurityConfig;
import com.creedboy.springbootboard.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Import(TestSecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mvc;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {

        // Given

        // When
//        mvc.perform(MockMvcRequestBuilders.get("/"))
//            .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("forward:/articles"))
            .andExpect(forwardedUrl("/articles"))
            .andDo(MockMvcResultHandlers.print());
    }

}