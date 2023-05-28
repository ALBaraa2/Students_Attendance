package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class TAEnrollmentCourse implements Initializable {

    @FXML
    private ComboBox<String> sTAid;

    @FXML
    private Label xz;

    @FXML
    private Button close;

    @FXML
    private ComboBox<String> sCourse;

    @FXML
    private ComboBox<String> sYear;

    @FXML
    private ComboBox<String> sSemester;
    @FXML
    private ComboBox<String> sSeId;

    DBModel db = new DBModel();
    public void initialize(URL url, ResourceBundle rb) {
        setTAid();
        setComboBoxes();
    }
    private void setTAid(){
        ObservableList<String> ids = FXCollections.observableList(db.getTA_id());
        sTAid.setItems(ids);
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        sCourse.setItems(ids);
        sCourse.setOnAction(this::handleCIcomAction);
    }

    @FXML
    private void handleCIcomAction(ActionEvent event) {
        if (sCourse.getValue() != null) {
            String selectedCourseID = sCourse.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYears(selectedCourseID));
            sYear.setItems(year);
            sYear.setOnAction(this::handleYcomAction);
        }
    }

    private void handleYcomAction(ActionEvent event) {
        int selectedYear;
        if (sYear.getValue() != null) {
            selectedYear = Integer.parseInt(sYear.getSelectionModel().getSelectedItem());
            if (sCourse.getValue() != null) {
                String selectedCourseID = sCourse.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedCourseID, selectedYear));
                sSemester.setItems(semesters);
            }
        }
    }

    Navigation nav = new Navigation();
    @FXML
    void backToCourse(ActionEvent event) {
        nav.navigateTo(close, nav.ADMIN_FXML);
    }

    @FXML
    void done(ActionEvent event) {
        String id = sTAid.getValue().trim();
        String course = sCourse.getValue().trim();
        String year = sYear.getValue().trim();
        String semester = sSemester.getValue().trim();

        if(db.checkEnrollments(course,year,semester)){
            xz.setTextFill(Color.RED);
            xz.setText("this course is already enrollments by " + db.getAssistantId(course,year,semester));
        }else{
            db.addEnrollment(course,year,semester,id);
                xz.setTextFill(Color.GREEN);
                xz.setText("                   successfully Enrollments");

        }
    }

}