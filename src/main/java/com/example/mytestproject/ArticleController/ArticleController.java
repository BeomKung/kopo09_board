package com.example.mytestproject.ArticleController;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.mytestproject.Service.CommentService;
import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // Simple Logging Facade for Java
public class ArticleController {

    private static final Logger log = LogManager.getLogger(ArticleController.class);
    //의존성 주입 = DI(Dependency Injection)
    @Autowired //이미 생성한 repo 객체를 DI
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/new")
    public String newArticleForm(Model model){
        return "articles/new"; //뷰에게 전달됨
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());
        log.info(form.toString());

        // DTO ==> Entity 변환
        Article article = form.toEntity(); // 실행하면 반환이 된다는 가정
        //System.out.println(article.toString());
        log.info(article.toString());

        // Repo로 Entity DB 저장

        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String articleID(@PathVariable Long id, Model model){ // URL로 전달된 변수값을 파랔ㅁ미터로 받겠다는애너메이션
        log.info("id = "+ id);


        // 1번 - id DB 조회
        //Optional<Article> articleEntity =  articleRepository.findById(id);
        Article articleEntity =  articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2번 - 모델에 데이터를 등록
        model.addAttribute("data",articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3번 - 사용자에게 화면전달
        return "articles/articleID";
    }

    @GetMapping("/articles/{id}/edit") //수정하기
    public String editArticle(@PathVariable Long id, Model model){ // URL로 전달된 변수값을 파랔ㅁ미터로 받겠다는애너메이션
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "articles/edit";  // 수정 폼
    }
    @PostMapping("/articles/update") //수정후 DB업뎃
    public String updateArticle(ArticleForm form) {
        Article article = articleRepository.findById(form.getId()).orElse(null);

        if (article != null) {
            article.setTitle(form.getTitle());
            article.setContent(form.getContent());
            articleRepository.save(article);
        }
//        if(dbData != null){
//            Article saved = articleRepository.save(entity);
//            log.info(saved.toString());
//        }

        return "redirect:/articles/" + form.getId();  // 수정한 글 상세보기로 리다이렉트
    }


    @GetMapping("/articles/index")
    public String index(Model model) {

        // 1번 DB에 모든 게시글 데이터 가져오기
        // Iterable -> 연속적인
        //Iterable<Article> articleList = articleRepository.findAll(); //업캐스팅
        //List<Article> articleList2 = (List<Article>) articleRepository.findAll(); //다운캐스팅
        List<Article> articleList = articleRepository.findAll();

        // 2번 등록하기m
        model.addAttribute("articleList",articleList);
        // 3번 화면 리턴
        return "articles/index";
    }

    //HTTP 매서드 = GET, POST, UPDATE, DELETE
    //HTML 매서드 = GET, POST

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청 확인 ");
        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());

        if(article != null){
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg","삭제가 완료됐수");
        }

        return "redirect:/articles/index";
    }

}
