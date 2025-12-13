package com.mysite.sbb.answer;

import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@RestController
public class ApiAnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    // UserService 제거됨

    // 답변 등록 (POST)
    @PostMapping("/{questionId}")
    public void create(@PathVariable("questionId") Integer questionId, @RequestBody AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(questionId);
        // 로그인 정보(Principal, SiteUser) 없이 내용만 저장
        this.answerService.create(question, answerForm.getContent());
    }

    // 답변 단건 조회 (GET)
    @GetMapping("/{id}")
    public AnswerDto getAnswer(@PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setContent(answer.getContent());
        dto.setCreateDate(answer.getCreateDate());
        dto.setQuestionId(answer.getQuestion().getId());
        // 작성자(author) 정보 설정 부분 제거됨
        return dto;
    }

    // 답변 수정 (PUT)
    @PutMapping("/{id}")
    public void modify(@PathVariable("id") Integer id, @RequestBody AnswerForm answerForm) {
        Answer answer = this.answerService.getAnswer(id);
        // 권한 체크 없이 바로 수정
        this.answerService.modify(answer, answerForm.getContent());
    }

    // 답변 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        // 권한 체크 없이 바로 삭제
        this.answerService.delete(answer);
    }
}