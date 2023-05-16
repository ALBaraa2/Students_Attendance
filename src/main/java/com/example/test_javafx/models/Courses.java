package com.example.test_javafx.models;


public class Courses {
    String course_id;
    String instructor_name;
    String course_name;
    String course_location;

    public Courses() {
    }

    public Courses(String course_id, String instructor_name, String course_name, String course_location){
        this.course_id = course_id;
        this.instructor_name = instructor_name;
        this.course_name = course_name;
        this.course_location = course_location;
    }

    public String getCourse_id() {return course_id;}

    public void setCourse_id(String course_id) {this.course_id = course_id;}

    public String getInstructor_name() {return instructor_name;}

    public void setInstructor_name(String instructor_name) {this.instructor_name = instructor_name;}

    public String getCourse_name() {return course_name;}

    public void setCourse_name(String course_name) {this.course_name = course_name;}

    public String getCourse_location() {return course_location;}

    public void setCourse_location(String course_location) {this.course_location = course_location;}
}
