package com.mysite.sbb.question;

import com.mysite.sbb.DateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            throw new DateNotFoundException("question not found");
        }
    }

    public Question create(String subject, String content){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
        return q;
    }
    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        this.questionRepository.save(question);

    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }
}
