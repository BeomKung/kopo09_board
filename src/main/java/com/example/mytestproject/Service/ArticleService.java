package com.example.mytestproject.Service;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    private static final Logger log = LogManager.getLogger(ArticleService.class);
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article articles(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto){
        Article entity = dto.toEntity();

        if(entity.getId() != null){
            return null;
        }
        return articleRepository.save(entity);
    }

    public Article patch(Long id, ArticleForm dto) {

        Article article = dto.toEntity();

        Article dbData = articleRepository.findById(id).orElse(null);
        log.info("id : {}, article : {}", id, article.toString());


        if (dbData == null || id != article.getId()){
            log.info("잘못된 요청, id : {}, article : {}",id, article.toString());
            return null;
        }

        dbData.patch(article); //수정해야될 부분만 업데이트가 되도록하는 함수만쓸거니까 여기에 들어걍됨

        //만약 id가 잘못되었을 경우 처리
        Article saveData = articleRepository.save(article);

        return saveData;
    }

    public Article delete(Long id) {
        Article dbData = articleRepository.findById(id).orElse(null);

        //만약에 요청이 잘못되었을경우의 처리
        if(dbData == null){
            return null;
        }

        //삭제 완료 -> 정상 삭제되었을경우 200
        articleRepository.delete(dbData);

        return dbData;
    }


    @Transactional
    public List<Article> createTransaction(List<ArticleForm> dtos) {
        //dtos를 엔티티 묶음으로 변환
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++){
            ArticleForm dto = dtos.get(i);
            Article entity = dto.toEntity();
            articles.add(entity);
        }

        List<Article> articlesStream = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        // DB에 저장
        articlesStream.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제로 에러 발생
        // findbyID 로 -1인 데이터 찾기 -> 당연히 업음
        // ==> orElseThrow() - 에러를 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("송금 실패"));


        // 결과 리턴
        return articlesStream;
    }
}
