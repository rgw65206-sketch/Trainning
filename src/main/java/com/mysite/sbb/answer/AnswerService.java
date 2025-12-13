package com.mysite.sbb.answer;

import com.mysite.sbb.DateNotFoundException; // 2장이면 DateNotFoundException 일 수 있습니다. (Data..인지 확인 필요)
import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 답변 생성
    public void create(Question question, String content){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }

    // 답변 조회 (추가됨)
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            // 프로젝트에 있는 예외 클래스 이름에 맞춰주세요 (DataNotFoundException 또는 DateNotFoundException)
            throw new DateNotFoundException("answer not found");
        }
    }

    // 답변 수정 (추가됨)
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        this.answerRepository.save(answer);
    }

    // 답변 삭제 (추가됨)
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
}