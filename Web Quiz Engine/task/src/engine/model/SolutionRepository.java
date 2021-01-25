package engine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface SolutionRepository extends PagingAndSortingRepository<QuizSolution, Long> {
    Page<QuizSolution> findByUserId(long userId, Pageable pageable);
}
