package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;


public class Report {

    @FXML
    private BorderPane root;

    Navigation nav = new Navigation();

    @FXML
    void SheetOfNonCompliant(ActionEvent event) {
        nav.navigateTo(root , nav.SHEET_OF_NON_COMPLIANT_FXML);
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root , nav.TEACH_ASSISTANT_FXML);
    }

    @FXML
    void sheetOfAttendance(ActionEvent event) {
        nav.navigateTo(root , nav.ATTENDANCE_SHEET_FXML);
    }

    @FXML
    void SheetOfLecture(ActionEvent event) {
        nav.navigateTo(root , nav.SHEET_OF_LECTURE);
    }
}

