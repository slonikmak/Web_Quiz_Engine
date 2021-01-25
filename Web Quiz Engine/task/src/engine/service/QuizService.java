package engine.service;

import engine.PermissionException;
import engine.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SolutionService solutionService;

    private final QuizAnswerResult successAnswer = new QuizAnswerResult(true, "Congratulations, you're right!");
    private final QuizAnswerResult failAnswer = new QuizAnswerResult(false, "Wrong answer! Please, try again.");

    public Quiz addQuiz(Quiz quiz){
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> getById(Long id){
        return quizRepository.findById(id);
    }

    public List<Quiz> getAll(){
        return ((List<Quiz>) quizRepository.findAll());
    }

    public Page<Quiz> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    public Optional<QuizAnswerResult> solve(ClientAnswer answer, Long id, User user) {
        return getById(id).map(q->{
            if (validateAnswer(q.getAnswer(), answer.getAnswer())) {
                solutionService.save(new QuizSolution(user.getId(), id));
                return successAnswer;
            }
            return failAnswer;
        });
    }

    public void deleteQuiz(long id, long userId){
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (quiz.get().getAuthorId() != userId) {
            throw new PermissionException();
        }
        quizRepository.deleteById(id);
    }

    private boolean validateAnswer(List<Integer> rightAnswer, List<Integer> answer) {
        if (rightAnswer == null) {
            if (answer.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
        if (rightAnswer.size() == answer.size()) {
            for (Integer integer : rightAnswer) {
                if (!answer.contains(integer)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
