package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Admin {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button courses;

    @FXML
    private Button createUserAccount;

    @FXML
    private Button taEnrollmentCourse;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void Courses(ActionEvent event) {nav.navigateTo(anchorPane, nav.COURSE_FXML);}

    @FXML
    void navToCreateUserAccount(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.Create_User_Account_FXML);
    }

    @FXML
    void navToTAEnrollmentCourse(ActionEvent event) {
        nav.navigateTo(anchorPane, nav.TA_Enrollment_Course_FXML);
    }

}
