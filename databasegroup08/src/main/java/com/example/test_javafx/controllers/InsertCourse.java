package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class InsertCourse {

    @FXML
    private BorderPane root;

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

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @FXML
    void backFromInsertCours(ActionEvent event) {
        nav.navigateTo(root, nav.COURSE_FXML);
    }

    @FXML
    void DONE(ActionEvent event) {
        if (!course_id.getText().equals("") && !course_name.getText().equals("") && !course_location.getText().equals("") && !instructor_name.getText().equals("")) {
            if (db.insertCourse(course_id.getText(), instructor_name.getText(), course_name.getText(), course_location.getText())) {
                faild.setText("Course added successfully");
                faild.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                faild.setTextFill(Color.GREEN);
                faild.setVisible(true);
                course_id.setText("");
                course_name.setText("");
                course_location.setText("");
                instructor_name.setText("");
            } else {
                faild.setText("Course_id :must be 4 letters and 4 number");
                faild.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                faild.setTextFill(Color.RED);
                faild.setVisible(true);
            }
        } else {
            faild.setText("Complete data registration");
            faild.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            faild.setVisible(true);
        }
    }
}