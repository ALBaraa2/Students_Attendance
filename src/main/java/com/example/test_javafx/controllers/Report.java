package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Report {

    @FXML
    private AnchorPane root;

    DBModel db = new DBModel();
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
}
