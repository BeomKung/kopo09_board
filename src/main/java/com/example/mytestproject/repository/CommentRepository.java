package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //특정 게시글의 달린 탯글을 모드 조회
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleID",
            nativeQuery = true) //JPQL(Java Persistance Query Language)
    List<Comment> findByArticleId(Long articleID);


    //특정 닉네임이 작성한 댓글 모두저회
    List<Comment> findByNickname(String nickname);
    //외부 파일에 작성된 쿼리문 인식 = native query XML

}
