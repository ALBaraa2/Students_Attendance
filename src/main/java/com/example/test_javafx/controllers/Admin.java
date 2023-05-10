package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Admin implements Initializable {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button createCourse;

    @FXML
    public Button createUserAccount;

    @FXML
    public Button taEnrollmentCourse;

    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void CreateCourse_FXML() {
        nav.navigateTo(anchorPane, nav.CREATRE_COURSE_FXML);

    }

    public void navToCreateUserAccount() {
        nav.navigateTo(anchorPane, nav.Create_User_Account_FXML);
    }

    public void navToTAEnrollmentCourse() {
        nav.navigateTo(anchorPane, nav.TA_Enrollment_Course_FXML);
    }
}
