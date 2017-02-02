package model;

import java.util.ArrayList;

public class Poll {
    private int id;
    private String question;
    private ArrayList<Answers> answers = new ArrayList<Answers>();

    public Poll(int id, String question, ArrayList<Answers> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Answers> getAnswers() {
        return answers;
    }
}
