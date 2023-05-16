package com.example.test_javafx.models;

public class SharedData {
    private static SharedData instance;
    private String course_id;
    private String year;
    private String semester;
    private String sec_id;

    private SharedData() {
    }

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String value) {
        course_id = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }
}

