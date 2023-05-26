package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class Attendance {

    @FXML
    private ComboBox<String> LName;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> lectureName;

    @FXML
    private Label massege;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private ComboBox<String> student;

    @FXML
    private TableView<?> table;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    @FXML
    void AttendanceReport(ActionEvent event) {

    }

    @FXML
    void Checkin(ActionEvent event) {
        if (student.getValue() != null){

        } else {

        }
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }
}
