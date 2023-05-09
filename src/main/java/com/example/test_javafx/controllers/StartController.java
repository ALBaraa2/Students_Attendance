package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {


    @FXML
    public AnchorPane rootPane;
    @FXML
    public Button nav_add_section;
    // nav_add_section,nav_lectures_times,nav_enroll_student
    @FXML
    public Button nav_lectures_times;
    @FXML
    public Button nav_enroll_student;
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void navToAddSection() {
        nav.navigateTo(rootPane, nav.ADD_SECTION_FXML);
    }

    public void navToEnrollStudent() {
        nav.navigateTo(rootPane, nav.ENROLL_STUDENT_FXML);
    }

    public void navToLecturesTimes() {
        nav.navigateTo(rootPane, nav.LECTURES_TIMES_FXML);
    }
}
