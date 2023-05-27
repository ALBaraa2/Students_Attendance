package com.example.test_javafx.models;

public class AttendanceSheet {
    String student_name;
    String student_id;
    String attendance_status;
    String lecture_title;

    double attendancePercentage;

    public AttendanceSheet(String lecture_title, String attendance_status){
        this.attendance_status = attendance_status;
        this.lecture_title = lecture_title;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public AttendanceSheet(String student_name, double attendancePercentage) {
        this.student_name = student_name;
        this.attendancePercentage = attendancePercentage;
    }

    public AttendanceSheet(String student_name,String student_id,String attendance_status ){
        this.student_name = student_name;
        this.student_id = student_id;
        this.attendance_status =attendance_status;

    }

    public String getLecture_title() {
        return lecture_title;
    }

    public void setLecture_title(String lecture_title) {
        this.lecture_title = lecture_title;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }


}
