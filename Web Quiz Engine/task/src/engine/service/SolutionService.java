package engine.service;

import engine.model.QuizSolution;
import engine.model.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolutionService {

    @Autowired
    private SolutionRepository repository;

    public void save(QuizSolution solution) {
        repository.save(solution);
    }

    public Page<QuizSolution> getAll(long userId, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        return repository.findByUserId(userId, paging);
    }
}
