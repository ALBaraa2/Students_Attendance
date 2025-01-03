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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class LectureSheet implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> LnameCom;

    @FXML
    private ComboBox<String> courseIDcom;

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

    @FXML
    private ComboBox<String> Csemester;

    @FXML
    private ComboBox<String> Cyear;

    @FXML
    private Label massege;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();
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
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs(email));
        courseIDcom.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseIDcom, ids);
        courseIDcom.setOnAction(this::setComboBoxesYear);
    }

    private void setComboBoxesYear(ActionEvent event1) {
        String course_id = courseIDcom.getSelectionModel().getSelectedItem();
        if (course_id != null) {
            ObservableList<String> years = FXCollections.observableList(db.getYearsToteachAssistant(email, course_id));
            Cyear.setItems(years);
            Cyear.setOnAction(this::setComboBoxesSemester);
        }
    }

    private void setComboBoxesSemester(ActionEvent event1) {
        String course_id = courseIDcom.getSelectionModel().getSelectedItem();
        String Syear = Cyear.getSelectionModel().getSelectedItem();
        if (course_id != null && Syear != null) {
            ObservableList<String> semesters = FXCollections.observableList(db.getSemestersToteachAssistant(email,
                    course_id, Syear));
            Csemester.setItems(semesters);
            Csemester.setOnAction(this::setComboBoxesLecture_name);
        }
    }

    private void setComboBoxesLecture_name(ActionEvent event) {
        String course_id = courseIDcom.getSelectionModel().getSelectedItem();
        String Syear = Cyear.getSelectionModel().getSelectedItem();
        String Ssemester = Csemester.getSelectionModel().getSelectedItem();
        ObservableList<String> lname = FXCollections.observableList(db.getLecturesName(course_id,
                Integer.parseInt(Syear), Ssemester));
        LnameCom.setItems(lname);
    }
    @FXML
    void viewLName(ActionEvent event) {
        if (courseIDcom.getValue() != null && Cyear.getValue() != null && Csemester.getValue() != null
        && LnameCom.getValue() != null) {
            table.setItems(FXCollections.observableArrayList(db.lectureSheet(email, courseIDcom.getValue(),
                    LnameCom.getValue(), Cyear.getValue(), Csemester.getValue())));
        } else {
            massege.setText("Complete Data");
            massege.setTextFill(Color.RED);
        }
    }
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root , nav.REPORT_FXML);
    }

}
