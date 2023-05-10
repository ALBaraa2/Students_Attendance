package com.example.test_javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Createcourse {

    @FXML
    private TableColumn<?, ?> course_id;

    @FXML
    private TableColumn<?, ?> course_location;

    @FXML
    private TableColumn<?, ?> course_name;

    @FXML
    private TableView<?> courses;

    @FXML
    private TableColumn<?, ?> instructor_name;

}
