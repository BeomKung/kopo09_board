package com.example.mytestproject.Service;

import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; //댓글관리 repo

    @Autowired
    private ArticleRepository articleRepository; //게시글 관리 repo

    public List<CommentDto> comments(Long articleId) {
//        articleId로 게시글에 달린개글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        //엔티티 -> dto 변환
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//
//        for(int i = 0 ; i < comments.size(); i++){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        // return dtos;


        // 결과리턴
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional //로직 중간ㅇ 실패되었을 경우 롤백 처리
    public CommentDto create(Long articleId, CommentDto dto) {
        //1. 게시글이 존재하는지 체크
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException(
                "댓글 생성에 실패하였습니다"+"요청한 게시글이 존재하지 않습니다"
        ));

        log.info(dto.toString());
        //2. 댓글 dto -> entity
        Comment comment = Comment.createComment(dto, article);
        //3. DB저장
        Comment created = commentRepository.save(comment);
        //4. DTO 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 수정하려는 대상이 DB에 있는지 조회
        Comment dbData = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글수정 실패" + "요청하신 원본댓글이 존재하지 않소"));
        // 원본데이터 수정
        dbData.patch(dto);

        // 수정데이터 갱신(DB)
        Comment updateData = commentRepository.save(dbData);

        // 변경된 데이터를 entity -> dto로 변환 후 반환
        return CommentDto.createCommentDto(updateData);


    }

    public CommentDto delete(Long id) {
        Comment dbData = commentRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("댓글 없어서 삭제[ 실패0"));


        commentRepository.delete(dbData);

        return CommentDto.createCommentDto(dbData);
    }
}
