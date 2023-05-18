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


    @FXML
    void Courses(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.COURSE_FXML);
    }

    @FXML
    void navToCreateUserAccount(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.CREATE_USER_ACCOUNT_FXML);
    }

    @FXML
    void navToTAEnrollmentCourse(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.TA_Enrollment_Course_FXML);

    }
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.LOGIN_FXML);
    }
}
