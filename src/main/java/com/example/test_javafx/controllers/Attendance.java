package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.AttendanceSheet;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
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

public class Attendance implements Initializable {

    @FXML
    private ComboBox<String> LName;

    @FXML
    private ComboBox<String> courseID;

    @FXML
    private Label massege;

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> sec_id;

    @FXML
    private TableColumn<AttendanceSheet,String> status;

    @FXML
    private TableColumn<AttendanceSheet,String> lecture_title;

    @FXML
    private ComboBox<String> student;

    @FXML
    private TableView<AttendanceSheet> table;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();
    String year = db.getYearSemester(email)[0];
    String semester = db.getYearSemester(email)[1];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.setCellValueFactory(new PropertyValueFactory<>("attendance_status"));
        lecture_title.setCellValueFactory(new PropertyValueFactory<>("lecture_title"));
        setComboBoxes();
        System.out.println(email);
        System.out.println(year);
        System.out.println(semester);
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs(email));
        courseID.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseID, ids);
        courseID.setOnAction(this::setComboBoxesSec_id);
    }
    private void setComboBoxesSec_id(ActionEvent event) {
        System.out.println(year +"\n"+semester);
        String course_id = courseID.getSelectionModel().getSelectedItem();
        ObservableList<String> sec_ids = FXCollections.observableList(db.getSecIds(course_id,
                Integer.parseInt(year), semester));
        sec_id.setItems(sec_ids);
        sec_id.setOnAction(this::setComboBoxesStudent);
        sec_id.setOnAction(this::setComboBoxesLecturename);
    }
    private void setComboBoxesLecturename(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        ObservableList<String> lectureNames = FXCollections.observableList(db.getLecturesTitle(course_id,
                Integer.parseInt(year), semester, Integer.parseInt(secid)));
        LName.setItems(lectureNames);
        CmboBoxAutoComplete.cmboBoxAutoComplete(LName, lectureNames);
    }
    private void setComboBoxesStudent(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        ObservableList<String> students = FXCollections.observableList(db.getStudents(course_id,
                email, secid));
        student.setItems(students);
        CmboBoxAutoComplete.cmboBoxAutoComplete(student, students);
    }

    @FXML
    void AttendanceReport(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        String Student = student.getSelectionModel().getSelectedItem();
        table.setItems(FXCollections.observableArrayList(db.getAttendanceReport
                (course_id,Integer.parseInt(year), semester,Integer.parseInt(secid),Student)));
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
