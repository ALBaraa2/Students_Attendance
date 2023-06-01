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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentEnrollmentsInCourse implements Initializable {
        @FXML
        private Button Done;

        @FXML
        private Label xz;

        @FXML
        private Button bcakk;

        @FXML
        private ComboBox<String> sCourse;

        @FXML
        private ComboBox<String> sID;

        @FXML
        private ComboBox<String> sSection;

        @FXML
        private ComboBox<String> sSemester;

        @FXML
        private ComboBox<String> sYear;

        Navigation nav = new Navigation();
        DBModel db = new DBModel();


        public void initialize(URL url, ResourceBundle rb) {
                setComboBoxes();
                setStudentid();
        }

        @FXML
        private void setStudentid() {
                ObservableList<String> ids = FXCollections.observableList(db.getStudentID());
                sID.setItems(ids);
                CmboBoxAutoComplete.cmboBoxAutoComplete(sID , ids);
        }

        @FXML
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
                                sSemester.setOnAction(this::handleScomAction);
                        }
                }
        }

        private void handleScomAction(ActionEvent event) {
                int selectedYear;
                if (sYear.getValue() != null) {
                        selectedYear = Integer.parseInt(sYear.getSelectionModel().getSelectedItem());
                        if (sCourse.getValue() != null && sSemester.getValue() != null) {
                                String selectedCourseID = sCourse.getSelectionModel().getSelectedItem();
                                String selecteSemester = sSemester.getSelectionModel().getSelectedItem();
                                ObservableList<String> SecIds = FXCollections.observableList(db.getSecIds(selectedCourseID, selectedYear, selecteSemester));
                                sSection.setItems(SecIds);
                        }
                }
        }

        @FXML
        void backToCourse(ActionEvent event) {
                nav.navigateTo(bcakk, nav.STUDENT_FXML);
        }

        @FXML
        void done(ActionEvent event) {
                String id = sID.getValue().trim();
                String course = sCourse.getValue().trim();
                String year = sYear.getValue().trim();
                String semester = sSemester.getValue().trim();
                String sec = sSection.getValue().trim();
                if (db.checkEnrollmentsStudent(id,course,year,semester)) {
                        xz.setTextFill(Color.RED);
                        xz.setText("this course is already enrollments by same student");
                } else {
                        db.addEnrollmentStudent(course,year,semester,sec,id);
                        xz.setTextFill(Color.GREEN);
                        xz.setText("                   successfully Enrollments");
                }
        }
}


