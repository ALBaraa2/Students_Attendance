package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class InsertCourse {

    @FXML
    private TextField course_id;

    @FXML
    private TextField course_location;

    @FXML
    private TextField course_name;

    @FXML
    private Label faild;

    @FXML
    private TextField instructor_name;

    @FXML
    private BorderPane root;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @FXML
    void backFromInsertCours(ActionEvent event) {
        nav.navigateTo(root, nav.COURSE_FXML);
    }

    @FXML
    void insertcourse(ActionEvent event) {
        if (db.insertCourse(course_id.getText(), instructor_name.getText(), course_name.getText(), course_location.getText())){
            faild.setText("Course added successfully");
            faild.setVisible(true);
        }else {
            faild.setText("Course registration failed!");
            faild.setVisible(true);
        }
    }

}