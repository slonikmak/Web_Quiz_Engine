package engine.model;

import java.util.List;

public class ClientAnswer {
    private List<Integer> answer;

    public ClientAnswer() {
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "ClientAnswer{" +
                "answer=" + answer +
                '}';
    }
}
