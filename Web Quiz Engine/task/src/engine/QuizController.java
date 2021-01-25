package engine;

import engine.model.*;
import engine.service.QuizService;
import engine.service.SolutionService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @Autowired
    private SolutionService solutionService;

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id){
        return quizService.getById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy){

        return quizService.getAll(page, pageSize, sortBy);
    }

    @PostMapping(path = "/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, HttpServletRequest request){
        quiz.setAuthorId(getUserFromRequest(request).getId());
        return quizService.addQuiz(quiz);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public QuizAnswerResult solveQuiz(@RequestBody ClientAnswer answer, @PathVariable Long id, HttpServletRequest request) {
        return quizService.solve(answer, id, getUserFromRequest(request)).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Long id, HttpServletRequest request) {
        User user = getUserFromRequest(request);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user not found");
        }
        try {
            quizService.deleteQuiz(id, user.getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "/api/register")
    public void registerUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<QuizSolution> getSolutions(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "completedAt") String sortBy, HttpServletRequest request) {

        String userEmail = request.getUserPrincipal().getName();
        User user = userService.getByEmail(userEmail);
        return solutionService.getAll(user.getId(), page, pageSize, sortBy);
    }

    private User getUserFromRequest(HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        return userService.getByEmail(userEmail);
    }
}
