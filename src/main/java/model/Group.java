package model;

public class Group {
    private int id;
    private String name;
    private String screenName;
    private String photo50;
    private String photo100;
    private String photo200;

    public Group(int id, String name, String screenName, String photo50, String photo100, String photo200) {
        this.id = id;
        this.name = name;
        this.screenName = screenName;
        this.photo50 = photo50;
        this.photo100 = photo100;
        this.photo200 = photo200;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getPhoto200() {
        return photo200;
    }
}
