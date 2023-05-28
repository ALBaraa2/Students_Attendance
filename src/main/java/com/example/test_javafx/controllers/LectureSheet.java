package com.example.test_javafx.controllers;


import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.Lectures;
import com.example.test_javafx.models.SharedData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LectureSheet implements Initializable {

    @FXML
    private ComboBox<String> LnameCom;

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
    private TableColumn<Lectures, String> sec_id;

    @FXML
    private TableColumn<Lectures, String> semester;

    @FXML
    private TableColumn<Lectures, String> year;

    @FXML
    private TableView<Lectures> table;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();
    String y = db.getYearSemester(email)[0];
    String s = db.getYearSemester(email)[1];
    Lectures l = new Lectures();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        course_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        lecture_date.setCellValueFactory(new PropertyValueFactory<>("lecture_date"));
        lecture_id.setCellValueFactory(new PropertyValueFactory<>("lecture_id"));
        lecture_location.setCellValueFactory(new PropertyValueFactory<>("hall"));
        lecture_time.setCellValueFactory(new PropertyValueFactory<>("lecture_time"));
        sec_id.setCellValueFactory(new PropertyValueFactory<>("sec_id"));
        semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> lname = FXCollections.observableList(db.getLecturesTitle(l.getCourse_id(),Integer.parseInt(y) , s , l.getSec_id()));
        LnameCom.setItems(lname);
        CmboBoxAutoComplete.cmboBoxAutoComplete(LnameCom, lname);
    }
    @FXML
    void viewLName(ActionEvent event) {
        table.setItems(FXCollections.observableArrayList(db.lectureSheet(email ,LnameCom.getValue())));
    }

}