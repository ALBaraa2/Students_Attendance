package com.example.test_javafx.models;

public class LectureTime {
    String course_id;
    String course_title;
    String building;
    String room_number;
    String time_slot;

    public LectureTime(String course_id, String course_title, String building, String room_number, String time_slot) {
        this.course_id = course_id;
        this.course_title = course_title;
        this.building = building;
        this.room_number = room_number;
        this.time_slot = time_slot;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

}
