package engine.model;

public class QuizAnswerResult {
    private boolean success;
    private String feedback;

    public QuizAnswerResult() {
    }

    public QuizAnswerResult(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

}
