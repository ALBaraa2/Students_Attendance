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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Attendance implements Initializable {

    @FXML
    private ComboBox<String> LName;

    @FXML
    private ComboBox<String> courseID;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> lectureName;

    @FXML
    private Label massege;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> sec_id;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private ComboBox<String> student;

    @FXML
    private TableView<?> table;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();
    String year = db.getYearSemester(email)[0];
    String semester = db.getYearSemester(email)[1];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs(email));
        courseID.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseID, ids);
        courseID.setOnAction(this::setComboBoxesSec_id);
    }
    private void setComboBoxesSec_id(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        ObservableList<String> sec_ids = FXCollections.observableList(db.getSecIds(course_id,
                Integer.parseInt(year), semester));
        sec_id.setItems(sec_ids);
        sec_id.setOnAction(this::setComboBoxesLecturename);
    }
    private void setComboBoxesLecturename(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        ObservableList<String> lectureNames = FXCollections.observableList(db.getSecIds(course_id,
                Integer.parseInt(year), semester));
        LName.setItems(lectureNames);
        CmboBoxAutoComplete.cmboBoxAutoComplete(LName, lectureNames);
        LName.setOnAction(this::setComboBoxesStudent);
    }
    private void setComboBoxesStudent(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        ObservableList<String> students = FXCollections.observableList(db.getStudents(course_id,
                email, secid, LName.getValue()));
        student.setItems(students);
        CmboBoxAutoComplete.cmboBoxAutoComplete(student, students);
    }

    @FXML
    void AttendanceReport(ActionEvent event) {

    }

    @FXML
    void Checkin(ActionEvent event) {
        if (student.getValue() != null && LName.getValue() != null && courseID.getValue() != null && sec_id.getValue() != null){
            if (db.attendance(student.getValue(),courseID.getValue(),email,sec_id.getValue(),LName.getValue())){
                massege.setText("Done");
                massege.setTextFill(Color.GREEN);
                massege.setVisible(true);
            } else {
                massege.setText("Faild");
                massege.setTextFill(Color.RED);
                massege.setVisible(true);
            }
        } else {
            massege.setText("Complete data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }
}
