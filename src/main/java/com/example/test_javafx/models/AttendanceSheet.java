package com.example.test_javafx.models;

public class AttendanceSheet {
    String student_name;
    String student_id;
    String attendance_status;

    double attendancePercentage;

    public AttendanceSheet(String attendance_status){
        this.attendance_status = attendance_status;
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
