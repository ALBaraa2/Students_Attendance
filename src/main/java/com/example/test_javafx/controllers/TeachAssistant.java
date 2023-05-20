package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class TeachAssistant {


    @FXML
    private BorderPane root;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @FXML
    void backToLogin(ActionEvent event) {
        nav.navigateTo(root, nav.LOGIN_FXML);
    }

    @FXML
    void attendance(ActionEvent event) {
        nav.navigateTo(root, nav.ATTENDANCE_FXML);
    }

    @FXML
    void editLectures(ActionEvent event) {
        nav.navigateTo(root, nav.LECTURES_FXML);
    }

    @FXML
    void editStudant(ActionEvent event) {
        nav.navigateTo(root, nav.STUDENT_FXML);
    }

    @FXML
    void reports(ActionEvent event) {
        nav.navigateTo(root, nav.REPORT_FXML);
    }

}
