package com.example.test_javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class Navigation {
    public final String LOGIN_FXML = "views/login.fxml";
    public final String ADMIN_FXML = "views/admin.fxml";
    public final String TEACH_ASSISTANT_FXML = "views/teachassistant.fxml";
    public final String COURSE_FXML = "views/course.fxml";
    public final String ADD_SECTION_FXML = "views/addsection.fxml";
    public final String Student_Registration = "views/StudentRegistration.fxml";
    public final String ModifyStudentData = "views/modifystudentdata.fxml";
    public final String INSERT_COURSE_FXML = "views/insertcourse.fxml";
    public final String MODIFY_COURSE_FXML = "views/modifycourse.fxml";
    public final String StudentEnrollmentsInCourse = "views/studentenrollmentsincourse.fxml";
    public final String ModifyAStudentSDataInACourse = "views/ModifyAStudent'sDataInACourse.fxml";
    public final String CREATE_USER_ACCOUNT_FXML = "views/createuseraccount.fxml";
    public final String TA_Enrollment_Course_FXML = "views/taenrollmentcourse.fxml";
    public final String ATTENDANCE_FXML = "views/attendance.fxml";
    public final String LECTURES_FXML = "views/lecture.fxml";
    public final String INSERT_LECTURSE_FXML = "views/insertlecture.fxml";
    public final String MODIFY_LECTURE_FXML = "views/modifyLectuer.fxml";
    public final String STUDENT_FXML = "views/student.fxml";
    public final String REPORT_FXML = "views/report.fxml";
    public final String ATTENDANCE_SHEET_FXML = "views/attendancesheet.fxml";
    public final String SHEET_OF_NON_COMPLIANT_FXML = "views/sheetofnoncompliant.fxml";
    public final String SHEET_OF_LECTURE = "views/lecturesheet.fxml";

    public void navigateTo(Parent rootPane, String path) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            rootPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
