package model;

public class Answers {
    private int id;
    private String text;

    public Answers(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
