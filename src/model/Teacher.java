package model;

public class Teacher {
    private int teacherId;
    private String name;
    private String course;
    private String username;
    private String password;

    public Teacher(int teacherId, String name, String course, String username, String password) {
        this.teacherId = teacherId;
        this.name = name;
        this.course = course;
        this.username = username;
        this.password = password;
    }

    public Teacher() {
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
