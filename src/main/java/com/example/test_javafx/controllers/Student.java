package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import java.net.URL;
import java.util.ResourceBundle;

public class Student implements Initializable {


    @FXML
    private Button SM;

    @FXML
    private Button SR;

    @FXML
    private Button ME;

    @FXML
    private Button SE;

    @FXML
    private Button back;

    Navigation nav = new Navigation();
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void SR(ActionEvent event) {
        nav.navigateTo(SR, nav.Student_Registration);
    }

    @FXML
    void SEe(ActionEvent event) {
        nav.navigateTo(SE, nav.StudentEnrollmentsInCourse);
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(back, nav.TEACH_ASSISTANT_FXML);
    }

    @FXML
    void SMM(ActionEvent event) {
        nav.navigateTo(SM, nav.ModifyStudentData);
    }

    @FXML
    void MEE(ActionEvent event) {
        nav.navigateTo(ME, nav.ModifyAStudentSDataInACourse);
    }

    }


