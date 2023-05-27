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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAStudentSDataInACourse implements Initializable {

    DBModel db = new DBModel();


    Navigation nav = new Navigation();


    @FXML
    private ComboBox<String> NcourseId;

    @FXML
    private Label xz;

    @FXML
    private ComboBox<String> Nsection;

    @FXML
    private ComboBox<String> Nsemester;

    @FXML
    private ComboBox<String> Nyear;

    @FXML
    private AnchorPane O;

    @FXML
    private ComboBox<String> OcourseId;

    @FXML
    private ComboBox<String> Osection;

    @FXML
    private ComboBox<String> Osemester;

    @FXML
    private ComboBox<String> Oyear;

    @FXML
    private Button back;

    @FXML
    private Button done;

    @FXML
    private ComboBox<String> sId;

    public void initialize(URL url, ResourceBundle rb) {
        setStudentid();
        setComboBoxes2();
    }

    @FXML
    private void setStudentid() {
        ObservableList<String> ids = FXCollections.observableList(db.getStudentID());
            sId.setItems(ids);
            sId.setOnAction(this::setComboBoxeAction);

        }


    @FXML
    private void setComboBoxeAction(ActionEvent event){
        if (sId.getValue() != null) {
            String selectStudentID = sId.getSelectionModel().getSelectedItem();
            ObservableList<String> courses = FXCollections.observableList(db.getStudentCourses(selectStudentID));
            OcourseId.setItems(courses);
            OcourseId.setOnAction(this::handleCIcomAction );
        }
    }


    @FXML
    private void setComboBoxes2() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        NcourseId.setItems(ids);
        NcourseId.setOnAction(this::handleCIcomAction2);
    }

    @FXML
    private void handleCIcomAction(ActionEvent event) {
        if (OcourseId.getValue() != null && sId.getValue() != null) {
            String selectedCourseID = OcourseId.getSelectionModel().getSelectedItem();
            String selectStudentID = sId.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYearsStudent(selectStudentID,selectedCourseID));
            Oyear.setItems(year);
            Oyear.setOnAction(this::handleYcomAction);
        }
    }

    @FXML
    private void handleCIcomAction2(ActionEvent event) {
        if (NcourseId.getValue() != null) {
            String selectedCourseID = NcourseId.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYears(selectedCourseID));
            Nyear.setItems(year);
            Nyear.setOnAction(this::handleYcomAction2);
        }
    }

    private void handleYcomAction(ActionEvent event) {
        int selectedYear;
        if (Oyear.getValue() != null && OcourseId.getValue() != null && sId.getValue() != null ) {
            String selectStudentID = sId.getSelectionModel().getSelectedItem();
            selectedYear = Integer.parseInt(Oyear.getSelectionModel().getSelectedItem());
            if (OcourseId.getValue() != null) {
                String selectedCourseID = OcourseId.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemestersStudent(selectStudentID,selectedCourseID, selectedYear));
                Osemester.setItems(semesters);
                Osemester.setOnAction(this::handleScomAction);
            }
        }
    }

    private void handleYcomAction2(ActionEvent event) {
        int selectedYear;
        if (Nyear.getValue() != null) {
            selectedYear = Integer.parseInt(Nyear.getSelectionModel().getSelectedItem());
            if (NcourseId.getValue() != null) {
                String selectedCourseID = NcourseId.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedCourseID, selectedYear));
                Nsemester.setItems(semesters);
                Nsemester.setOnAction(this::handleScomAction2);
            }
        }
    }

    private void handleScomAction(ActionEvent event) {
        int selectedYear;
        if (Oyear.getValue() != null) {
            String selectStudentID = sId.getSelectionModel().getSelectedItem();
            selectedYear = Integer.parseInt(Oyear.getSelectionModel().getSelectedItem());
            if (OcourseId.getValue() != null && Osemester.getValue() != null) {
                String selectedCourseID = OcourseId.getSelectionModel().getSelectedItem();
                String selecteSemester = Osemester.getSelectionModel().getSelectedItem();
                ObservableList<String> SecIds = FXCollections.observableList(db.getSecIdsStudent(selectStudentID,selectedCourseID, selectedYear, selecteSemester));
                Osection.setItems(SecIds);
            }
        }
    }

    private void handleScomAction2(ActionEvent event) {
        int selectedYear;
        if (Nyear.getValue() != null) {
            selectedYear = Integer.parseInt(Nyear.getSelectionModel().getSelectedItem());
            if (OcourseId.getValue() != null && Osemester.getValue() != null) {
                String selectedCourseID = NcourseId.getSelectionModel().getSelectedItem();
                String selecteSemester = Nsemester.getSelectionModel().getSelectedItem();
                ObservableList<String> SecIds = FXCollections.observableList(db.getSecIds(selectedCourseID, selectedYear, selecteSemester));
                Nsection.setItems(SecIds);
            }
        }
    }

    @FXML
    void backToCourse(ActionEvent event) {
        nav.navigateTo(back, nav.STUDENT_FXML);
    }

    @FXML
    void done(ActionEvent event) {

        String id = sId.getValue().trim();

        String Ocourse = OcourseId.getValue().trim();
        String OOyear = Oyear.getValue().trim();
        String OOsemester = Osemester.getValue().trim();
        String Osec = Osection.getValue().trim();

        String Ncourse = NcourseId.getValue().trim();
        String NNyear = Nyear.getValue().trim();
        String NNsemester = Nsemester.getValue().trim();
        String Nsec = Nsection.getValue().trim();


        if(db.checkEnrollmentsStudent(id,Ncourse,NNyear,NNsemester,Nsec)){
            xz.setTextFill(Color.RED);
            xz.setText("this course is already enrollments by same student");
        }else {
            xz.setTextFill(Color.GREEN);
            db.deleteEnrollmentStudent(Ocourse, OOyear, OOsemester, Osec, id);
            db.addEnrollmentStudent(Ncourse, NNyear, NNsemester, Nsec, id);
            xz.setText("                   successfully Enrollments");
        }
    }
}
