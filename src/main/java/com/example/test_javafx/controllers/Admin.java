package com.example.test_javafx.controllers;
import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Admin {

    @FXML
    private AnchorPane anchorPane;

    Navigation nav = new Navigation();

    DBModel db = new DBModel();


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


}
