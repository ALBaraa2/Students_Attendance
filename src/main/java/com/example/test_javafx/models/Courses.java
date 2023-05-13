package com.example.test_javafx.models;

import org.rapidpm.modul.javafx.textfield.autocomplete.AutoCompleteElement;

public class Courses extends AutoCompleteElement {
    String course_id;
    String instructor_name;
    String course_name;
    String course_location;
    String year;

    public Courses(String course_id, String instructor_name, String course_name, String course_location, String year){
        this.course_id = course_id;
        this.instructor_name = instructor_name;
        this.course_name = course_name;
        this.course_location = course_location;
        this.year = year;
    }

    public String getCourse_id() {return course_id;}

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCourse_id(String course_id) {this.course_id = course_id;}

    public String getInstructor_name() {return instructor_name;}

    public void setInstructor_name(String instructor_name) {this.instructor_name = instructor_name;}

    public String getCourse_name() {return course_name;}

    public void setCourse_name(String course_name) {this.course_name = course_name;}

    public String getCourse_location() {return course_location;}

    public void setCourse_location(String course_location) {this.course_location = course_location;}
}
