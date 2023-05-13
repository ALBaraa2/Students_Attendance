package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.Lectures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @FXML
    private ComboBox<String> CIcom;

    @FXML
    private ComboBox<String> SIcom;

    @FXML
    private Label massege;

    @FXML
    private Label massege1;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();


    @FXML
    private void handleCIcomAction(ActionEvent event) {
        String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
        ObservableList<String> sec_ids = FXCollections.observableList(db.getSecIds(selectedCourseID));
        SIcom.setItems(sec_ids);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course_id.setCellValueFactory(new PropertyValueFactory<>("Course_id"));
        lecture_id.setCellValueFactory(new PropertyValueFactory<>("lecture_id"));
        lecture_title.setCellValueFactory(new PropertyValueFactory<>("lecture_title"));
        lecture_time.setCellValueFactory(new PropertyValueFactory<>("lecture_time"));
        lecture_date.setCellValueFactory(new PropertyValueFactory<>("lecture_date"));
        lecture_location.setCellValueFactory(new PropertyValueFactory<>("Hall"));
        setComboBoxes();
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        CIcom.setItems(ids);
        CIcom.setOnAction(this::handleCIcomAction);
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
        if (CIcom.getValue() == null || SIcom.getValue() == null){
            massege.setText("select course_id");
            massege1.setText("and sec_id");
            massege.setTextFill(Color.RED);
            massege1.setTextFill(Color.RED);
            massege1.setVisible(true);
            massege.setVisible(true);
        } else {
            lectures.setItems(FXCollections.observableArrayList(db.getLectures(CIcom.getValue(),
                    Integer.parseInt(SIcom.getValue()))));
        }
    }

}
