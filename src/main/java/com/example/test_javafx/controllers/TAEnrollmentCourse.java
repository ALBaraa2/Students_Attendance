package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
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
    private Label name;

    Navigation nav = new Navigation();
    DBModel db = new DBModel();

    public void initialize(URL url, ResourceBundle rb) {
        setTAid();
        setComboBoxes();
        if (sTAid.getValue() != null && sCourse.getValue() != null && sYear.getValue() != null && sSemester.getValue() != null) {
            sTAid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    name.setText(db.getTeachAssistantName(newValue));
                } else {
                    name.setText("");
                }
            });
        }
    }

    private void setTAid(){
        ObservableList<String> ids = FXCollections.observableList(db.getTA_id());
        sTAid.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(sTAid , ids);
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        sCourse.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(sCourse , ids);
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
    @FXML
    void backToCourse(ActionEvent event) {
        nav.navigateTo(close, nav.ADMIN_FXML);
    }

    @FXML
    void done(ActionEvent event) {
        String id = sTAid.getValue();
        String course = sCourse.getValue();
        String year = sYear.getValue();
        String semester = sSemester.getValue();
        if (id != null && course != null && year != null && semester != null) {
            if (db.checkEnrollments(course, year, semester)) {
                xz.setTextFill(Color.RED);
                xz.setText("This course is already enrolled by " + db.getAssistantId(course, year, semester));
            } else {
                db.addEnrollment(course, year, semester, id);
                xz.setTextFill(Color.GREEN);
                xz.setText("Successfully enrolled.");
            }
        } else {
            xz.setTextFill(Color.RED);
            xz.setText("Complete the data");
        }
    }
}