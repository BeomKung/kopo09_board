package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정_아이디_조회하여_테스트") //테스트 결과 이름 출력
    void findByArticleId() {
        // 6번 게시글을 조회하는 댓글 조회
        // 예상데이터 = expected
        Long articleID = 1L;


        Article article = new Article(1L,"1111","1111");
//        Comment fir = new Comment(7L, article, "Winston", "바나나를 좋아함");
//        Comment sec = new Comment(8L, article, "Ana", "총을 잘쏨");
//        Comment thr = new Comment(9L, article, "Juno", "우주에서옴");
        List<Comment> expected = Arrays.asList();

        // 실제데이터
        List<Comment> commentList = commentRepository.findByArticleId(articleID);
        // 비교
        assertEquals(expected.toString(), commentList.toString(), "1번 게시글은 댓글없음");
    }

    @Test
    @DisplayName("특정 닉네임으로 댓글 조회")
    void findByNickname() {
        String nickname = "Ana";
        //예상데이터
        Comment fir = new Comment(3L,
                new Article(4L, "나의 최애는?", "ㄱㄱㄱㄱ"),
                nickname, "콩이");
        Comment sec = new Comment(5L,
                new Article(5L, "나의 인생 언어는?", "댓 ㄱ"),
                nickname, "JAVA");
        Comment thr = new Comment(8L,
                new Article(6L, "나의 장점은?", "대댓 ㄱㄱㄱㄱ"),
                nickname, "총을 잘쏨");

        List<Comment> expected = Arrays.asList(fir,sec, thr);
        //실제데이터
        List<Comment> commentList = commentRepository.findByNickname(nickname);

        //비교
        assertEquals(expected.toString(), commentList.toString());
    }
}