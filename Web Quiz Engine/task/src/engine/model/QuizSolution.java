package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class QuizSolution {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @JsonIgnore
    private long userId;

    @JsonProperty("id")
    private long quizId;

    @CreationTimestamp
    private Date completedAt;

    public QuizSolution() {
    }

    public QuizSolution(long userId, long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getQuizId() {
        return quizId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }
}
