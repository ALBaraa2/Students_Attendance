package com.example.test_javafx.models;

import java.sql.Time;
import java.util.Date;

public class Lectures {
    String course_id;
    String lecture_id;
    String lecture_title;
    Time lecture_time;
    Date lecture_data;
    String lecture_location;

    public Lectures(String course_id, String lecture_id, String lecture_title, Time lecture_time, Date lecture_data, String lecture_location) {
        this.course_id = course_id;
        this.lecture_id = lecture_id;
        this.lecture_title = lecture_title;
        this.lecture_time = lecture_time;
        this.lecture_data = lecture_data;
        this.lecture_location = lecture_location;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(String lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getLecture_title() {
        return lecture_title;
    }

    public void setLecture_title(String lecture_title) {
        this.lecture_title = lecture_title;
    }

    public Time getLecture_time() {
        return lecture_time;
    }

    public void setLecture_time(Time lecture_time) {
        this.lecture_time = lecture_time;
    }

    public Date getLecture_data() {
        return lecture_data;
    }

    public void setLecture_data(Date lecture_data) {
        this.lecture_data = lecture_data;
    }

    public String getLecture_location() {
        return lecture_location;
    }

    public void setLecture_location(String lecture_location) {
        this.lecture_location = lecture_location;
    }
}