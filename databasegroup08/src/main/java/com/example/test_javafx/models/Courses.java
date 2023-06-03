package com.example.test_javafx.models;


public class Courses {
    String course_id;
    String instructor_name;
    String course_name;
    String course_location;
    String year;
    String semester;
    String sec_id;

    public Courses(String course_id, String instructor_name, String course_name, String course_location, String year,
                   String semester, String sec_id) {
        this(course_id, instructor_name, course_name, course_location);
        this.year = year;
        this.semester = semester;
        this.sec_id = sec_id;
    }

    public Courses(String course_id, String instructor_name, String course_name, String course_location){
        this.course_id = course_id;
        this.instructor_name = instructor_name;
        this.course_name = course_name;
        this.course_location = course_location;
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

    public String getCourse_id() {return course_id;}

    public void setCourse_id(String course_id) {this.course_id = course_id;}

    public String getInstructor_name() {return instructor_name;}

    public void setInstructor_name(String instructor_name) {this.instructor_name = instructor_name;}

    public String getCourse_name() {return course_name;}

    public void setCourse_name(String course_name) {this.course_name = course_name;}

    public String getCourse_location() {return course_location;}

    public void setCourse_location(String course_location) {this.course_location = course_location;}
}
