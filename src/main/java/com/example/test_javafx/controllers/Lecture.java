package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.Lectures;
import javafx.beans.binding.Bindings;
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

    @FXML
    private ComboBox<String> CIcom;

    @FXML
    private ComboBox<String> SIcom;

    @FXML
    private Label massege;

    @FXML
    private Label massege1;

    @FXML
    private ComboBox<String> Scom;

    @FXML
    private ComboBox<String> Ycom;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();


    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        CIcom.setItems(ids);
        CIcom.setOnAction(this::handleCIcomAction);
    }

    @FXML
    private void handleCIcomAction(ActionEvent event) {
        if (CIcom.getValue() != null) {
            String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYears(selectedCourseID));
            Ycom.setItems(year);
            Ycom.setOnAction(this::handleYcomAction);
        }
    }

    private void handleYcomAction(ActionEvent event) {
        int selectedYear;
        if (Ycom.getValue() != null) {
            selectedYear = Integer.parseInt(Ycom.getSelectionModel().getSelectedItem());
            if (CIcom.getValue() != null) {
                String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedCourseID, selectedYear));
                Scom.setItems(semesters);
                Scom.setOnAction(this::handleScomAction);
            }
        }
    }

    private void handleScomAction(ActionEvent event) {
        int selectedYear;
        if (Ycom.getValue() != null) {
            selectedYear = Integer.parseInt(Ycom.getSelectionModel().getSelectedItem());
            if (CIcom.getValue() != null && Scom.getValue() != null) {
                String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
                String selecteSemester = Scom.getSelectionModel().getSelectedItem();
                ObservableList<String> SecIds = FXCollections.observableList(db.getSecIds(selectedCourseID, selectedYear, selecteSemester));
                SIcom.setItems(SecIds);
            }
        }
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

    @FXML
    void backToTeachassistant(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void insert(ActionEvent event) {
        nav.navigateTo(root, nav.INSERT_LECTURSE_FXML);
    }

    @FXML
    void modify(ActionEvent event) {

    }

    @FXML
    void viewLectuers(ActionEvent event) {
        if (CIcom.getValue() == null || SIcom.getValue() == null){
            massege1.setText("Enter all data ");
            massege1.setTextFill(Color.RED);
            massege1.setVisible(true);
        } else {
            lectures.setItems(FXCollections.observableArrayList(db.getLectures(CIcom.getValue(), Ycom.getValue(), Scom.getValue(),
                    Integer.parseInt(SIcom.getValue()))));
            CIcom.setValue(null);
            Ycom.setValue(null);
            Scom.setValue(null);
            SIcom.setValue(null);
        }
    }

}
