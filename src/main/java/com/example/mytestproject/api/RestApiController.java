package com.example.mytestproject.api;

import com.example.mytestproject.Service.ArticleService;
import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RestApiController {

    private static final Logger log = LogManager.getLogger(RestApiController.class);
    @Autowired //이미 생성한 repo 객체를 DI 의존성주입
    // 인터페이스는 구현체를 구현해야한다
    private ArticleService articleService;

    @GetMapping("/api/allArticles")
    public List<Article> AllArticles(){
        return articleService.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article articles(@PathVariable Long id){
        return articleService.articles(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article serviceRes = articleService.create(dto);
        // 생성이 성공이었을때 /생성이 실패했을때
        // 삼항연산자
        return (serviceRes != null) ? ResponseEntity.status(HttpStatus.OK).body(serviceRes) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    @GetMapping("/api/allArticles")
//    public List<Article> AllArticles() {
//        // 데이터 리턴 -> 어떤? 게시물은 복수 : List<>
//        // 데이터는 어디? : DB 안에
//        // DB접근은 어떻게? : Repository
//        return articleRepository.findAll();
//    }
//
//    @GetMapping("/api/articles/{id}")
//    public Article articles(@PathVariable Long id){
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto){
//        // restapi는 json형태 이기때문에 그냥 dto가 아님
//        // 그러므로 @RequestBody 로 바디만 dto로 바꿀게임
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable Long id,
                                        @RequestBody ArticleForm dto){
        Article patchData = articleService.patch(id, dto);

        return (patchData != null) ?
                ResponseEntity.status(HttpStatus.OK).body(patchData) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        // 게시글의 ID, 수정할 내용도 획득 완료
//        // 수정할 dto를  -> Entity로 변환
//        Article article = dto.toEntity();
//        // db에 해당 인덱스 게시글 존재 확인
//        Article dbData = articleRepository.findById(id).orElse(null);
//        log.info("id : {}, article : {}", id, article.toString());
////        if (dbData != null) {
////            dbData.setTitle(dto.getTitle());
////            dbData.setContent(dto.getContent());
////            articleRepository.save(dbData);
////        }
//
//        if (dbData == null || id != article.getId()){
//            log.info("잘못된 요청, id : {}, article : {}",id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        dbData.patch(article); //수정해야될 부분만 업데이트가 되도록하는 함수만쓸거니까 여기에 들어걍됨
//
//        //만약 id가 잘못되었을 경우 처리
//        Article saveData = articleRepository.save(article);
//
//        return ResponseEntity.status(HttpStatus.OK).body(saveData);
    }
//
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article serviceRes = articleService.delete(id);
        return (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//        //삭제할 데이터가 DB에 존재하는지
//        Article dbData = articleRepository.findById(id).orElse(null);
//
//        //만약에 요청이 잘못되었을경우의 처리
//        if(dbData == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        //삭제 완료 -> 정상 삭제되었을경우 200
//        articleRepository.delete(dbData);
//
//        return ResponseEntity.status(HttpStatus.OK).body(null); //body(null) == build()
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createList = articleService.createTransaction(dtos);

        return (createList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
