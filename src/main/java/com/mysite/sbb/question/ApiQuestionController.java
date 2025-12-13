package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.answer.AnswerService; // AnswerService가 필요할 수 있음
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@RestController
public class ApiQuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    // 1. 다건 조회 (GET)
    @GetMapping("")
    public List<QuestionDto> list() {
        List<Question> questionList = this.questionService.getList();

        // Entity List -> DTO List 변환
        return questionList.stream().map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setId(q.getId());
            dto.setSubject(q.getSubject());
            dto.setContent(q.getContent());
            dto.setCreateDate(q.getCreateDate());

            // [추가된 부분] 답변 리스트도 DTO로 변환해서 넣기
            List<AnswerDto> answerDtos = new ArrayList<>();
            if(q.getAnswerList() != null) {
                for (Answer answer : q.getAnswerList()) {
                    AnswerDto aDto = new AnswerDto();
                    aDto.setId(answer.getId());
                    aDto.setContent(answer.getContent());
                    aDto.setCreateDate(answer.getCreateDate());
                    aDto.setQuestionId(q.getId());
                    answerDtos.add(aDto);
                }
            }
            dto.setAnswerList(answerDtos); // 변환된 답변 리스트 설정

            return dto;
        }).collect(Collectors.toList());
    }

    // 2. 단건 조회 (GET)
    @GetMapping("/{id}")
    public QuestionDto detail(@PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);

        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setSubject(question.getSubject());
        dto.setContent(question.getContent());
        dto.setCreateDate(question.getCreateDate());

        // 답변 리스트도 DTO로 변환해서 포함
        List<AnswerDto> answerDtos = new ArrayList<>();
        if(question.getAnswerList() != null) {
            for (Answer answer : question.getAnswerList()) {
                AnswerDto aDto = new AnswerDto();
                aDto.setId(answer.getId());
                aDto.setContent(answer.getContent());
                aDto.setCreateDate(answer.getCreateDate());
                aDto.setQuestionId(question.getId());
                answerDtos.add(aDto);
            }
        }
        dto.setAnswerList(answerDtos);
        return dto;
    }

    // 3. 질문 등록 (POST)
    @PostMapping("")
    public QuestionDto create(@RequestBody QuestionForm questionForm) {
        Question q = this.questionService.create(questionForm.getSubject(), questionForm.getContent());

        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setSubject(q.getSubject());
        dto.setContent(q.getContent());
        dto.setCreateDate(q.getCreateDate());
        return dto;
    }

    // 4. 질문 수정 (PUT)
    @PutMapping("/{id}")
    public QuestionDto modify(@PathVariable("id") Integer id, @RequestBody QuestionForm questionForm) {
        Question question = this.questionService.getQuestion(id);
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());

        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setSubject(question.getSubject());
        dto.setContent(question.getContent());
        dto.setCreateDate(question.getCreateDate());
        return dto;
    }

    // 5. 질문 삭제 (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content 반환
    public void delete(@PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        this.questionService.delete(question);
    }
}
