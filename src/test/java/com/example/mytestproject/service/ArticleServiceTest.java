package com.example.mytestproject.service;

import com.example.mytestproject.Service.ArticleService;
import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void findAll(){
        // 예측 가능한 데이터 작성
        Article first = new Article(1L, "1111", "1111");
        Article second = new Article(2L, "2222", "2222");
        Article third = new Article(3L, "3333", "3333");
        Article f = new Article(4L, "3333", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(first, second, third, f));

        // 실제 DB 데이터 수집
        List<Article> articleList = articleService.findAll();

        // 1번과 2번 비교
        assertEquals(expected.toString(), articleList.toString());
    }

    @Test
    @Transactional
    void articles_success() {
        //예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "1111", "1111");
        //실제 데이터
        Article article = articleService.articles(id);
        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void articles_fail() {
        //예상 데이터
        Long id = -1L;
        Article expected = null;
        //실제 데이터
        Article article = articleService.articles(id);
        //비교
        assertEquals(expected, article.toString());
    }

    @Test
    @Transactional
    void create_suc() {
        //예상데이터
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        //실제데이터
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_fail() {
        //예상데이터
        Long id = 4L;
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = null;
        //실제데이터
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected, article.toString());
    }

}
