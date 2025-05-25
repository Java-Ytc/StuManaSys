package model;

public class Stu {
    private String stuId;
    private String name;
    private String password;
    private Integer javaScore;
    private Integer osScore;

    public Stu(String stuId, String name, String password) {
        this.stuId = stuId;
        this.name = name;
        this.password = password;
    }

    public Stu() {
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getJavaScore() {
        return javaScore;
    }

    public void setJavaScore(Integer javaScore) {
        this.javaScore = javaScore;
    }

    public Integer getOsScore() {
        return osScore;
    }

    public void setOsScore(Integer osScore) {
        this.osScore = osScore;
    }
}
