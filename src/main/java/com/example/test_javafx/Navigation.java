package com.example.test_javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class Navigation {
    public final String MAIN_FXML = "views/start.fxml";
    public final String ADD_SECTION_FXML = "views/addNewSection.fxml";
    public final String LECTURES_TIMES_FXML = "views/lecturestimes.fxml";
    public final String ENROLL_STUDENT_FXML = "views/enrollstudent.fxml";
    public final String START_CONTROLLER_FXML = "views/start.fxml";
    public final String LOGIN_FXML = "views/login.fxml";
    public final String ADMIN_FXML = "views/admin.fxml";
    public final String TEACH_ASSISTANT_FXML = "views/teachassistant.fxml";
    public final String COURSE_FXML = "views/course.fxml";

    public final String Student_Registration = "views/StudentRegistration.fxml";

    public final String ModifyStudentData = "views/ModifyStudentData.fxml";
    public final String INSERT_COURSE_FXML = "views/insertcourse.fxml";
    public final String MODIFY_COURSE_FXML = "views/modifycourse.fxml";
    public final String CREATE_USER_ACCOUNT_FXML = "views/createuseraccount.fxml";
    public final String TA_Enrollment_Course_FXML = "views/taenrollmentcourse.fxml";
    public final String ATTENDANCE_FXML = "views/attendance.fxml";
    public final String LECTURES_FXML = "views/lecture.fxml";
    public final String INSERT_LECTURSE_FXML = "views/insertlecture.fxml";
    public final String STUDENT_FXML = "views/student.fxml";
    public final String REPORT_FXML = "views/report.fxml";

    public void navigateTo(Parent rootPane, String path) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            rootPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
