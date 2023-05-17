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

    Navigation nav = new Navigation();
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private AnchorPane AP;

    @FXML
    private Button SM;

    @FXML
    private Button SR;

    @FXML
    private Pane SRe;

    @FXML
    private Button back;

    @FXML
    private Pane main;

    @FXML
    void SR(ActionEvent event) {
        nav.navigateTo(SR, nav.Student_Registration);
    }
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(back, nav.TEACH_ASSISTANT_FXML);
    }
    @FXML
    void SM(ActionEvent event) {
        nav.navigateTo(back, nav.ModifyStudentData);
    }

    }


