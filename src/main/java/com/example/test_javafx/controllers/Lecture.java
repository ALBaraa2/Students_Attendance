package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.Lectures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Lecture implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<Lectures, String> course_id;

    @FXML
    private TableColumn<Lectures, String> lecture_date;

    @FXML
    private TableColumn<Lectures, String> lecture_id;

    @FXML
    private TableColumn<Lectures, String> lecture_location;

    @FXML
    private TableColumn<Lectures, String> lecture_time;

    @FXML
    private TableColumn<Lectures, String> lecture_title;

    @FXML
    private TableView<Lectures> lectures;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course_id.setCellValueFactory(new PropertyValueFactory<>("Course_id"));
        lecture_date.setCellValueFactory(new PropertyValueFactory<>("lecture_date"));
        lecture_id.setCellValueFactory(new PropertyValueFactory<>("lecture_id"));
        lecture_location.setCellValueFactory(new PropertyValueFactory<>("Hall"));
        lecture_time.setCellValueFactory(new PropertyValueFactory<>("lecture_time"));
        lecture_title.setCellValueFactory(new PropertyValueFactory<>("lecture_title"));
    }

    @FXML
    void backToTeachassistant(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void insert(ActionEvent event) {

    }

    @FXML
    void modify(ActionEvent event) {

    }

    @FXML
    void viewLectuers(ActionEvent event) {

    }

}
