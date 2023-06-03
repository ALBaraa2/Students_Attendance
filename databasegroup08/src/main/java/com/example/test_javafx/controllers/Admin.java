package com.example.test_javafx.controllers;
import com.example.test_javafx.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class Admin {

    @FXML
    private AnchorPane anchorPane;

    Navigation nav = new Navigation();

    @FXML
    private Button back;



    // الذهاب الى صفحة courses
    @FXML
    void Courses(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.COURSE_FXML);
    }
    //الذهاب الى صفحة تسجيل معيد او admin
    @FXML
    void navToCreateUserAccount(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.CREATE_USER_ACCOUNT_FXML);
    }
    //الذهاب الى صفحة ربط المعيد بالcourse
    @FXML
    void navToTAEnrollmentCourse(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.TA_Enrollment_Course_FXML);

    }
    //الرجوع من صفحة الadmin الى صفحة login
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.LOGIN_FXML);
    }
}
