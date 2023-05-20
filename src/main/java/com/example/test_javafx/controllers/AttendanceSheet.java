package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.Lectures;
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
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceSheet implements Initializable  {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<com.example.test_javafx.models.AttendanceSheet> sheet;

    @FXML
    private TableColumn<Lectures, String> student_name;

    @FXML
    private TableColumn<Lectures, String> student_id;

    @FXML
    private TableColumn<Lectures, String> attendance_status;

    @FXML
    private ComboBox<String> course_idCom;

    @FXML
    private ComboBox<String> lecture_idCom;

    @FXML
    private ComboBox<String> sec_idCom;

    @FXML
    private ComboBox<String> semesterCom;


    @FXML
    private ComboBox<String> yearCom;


    @FXML
    private Label massage;

    @FXML
    private Label attendance_percentage;

    @FXML
    private Label attendance_count;


    @FXML
    private Label student_count;

    DBModel db = new DBModel();

    Navigation nav = new Navigation();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        student_name.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        student_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        attendance_status.setCellValueFactory(new PropertyValueFactory<>("attendance_status"));
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDsFromAttendance());
        course_idCom.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(course_idCom , ids);
        course_idCom.setOnAction(this::handleCIcomAction);
    }

    @FXML
    private void handleCIcomAction(ActionEvent event) {
        if (course_idCom.getValue() != null) {
            String selectedCourseID = course_idCom.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYears(selectedCourseID));
            yearCom.setItems(year);
            yearCom.setOnAction(this::handleYcomAction);
        }
    }

    private void handleYcomAction(ActionEvent event) {
        int selectedYear;
        if (yearCom.getValue() != null) {
            selectedYear = Integer.parseInt(yearCom.getSelectionModel().getSelectedItem());
            if (course_idCom.getValue() != null) {
                String selectedCourseID = course_idCom.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedCourseID, selectedYear));
                semesterCom.setItems(semesters);
                semesterCom.setOnAction(this::handleScomAction);
            }
        }
    }

    private void handleScomAction(ActionEvent event) {
        int selectedYear;
        if (yearCom.getValue() != null) {
            selectedYear = Integer.parseInt(yearCom.getSelectionModel().getSelectedItem());
            if (course_idCom.getValue() != null && semesterCom.getValue() != null) {
                String selectedCourseID = course_idCom.getSelectionModel().getSelectedItem();
                String selecteSemester = semesterCom.getSelectionModel().getSelectedItem();
                ObservableList<String> SecIds = FXCollections.observableList(db.getSecIds(selectedCourseID, selectedYear, selecteSemester));
                sec_idCom.setItems(SecIds);
                sec_idCom.setOnAction(this::handleLecture_idComAction);
            }
        }
    }

    private void handleLecture_idComAction(ActionEvent event) {
        String selectedYear;
        if (yearCom.getValue() != null) {
            selectedYear = yearCom.getSelectionModel().getSelectedItem();
            if (course_idCom.getValue() != null && semesterCom.getValue() != null && sec_idCom.getValue() != null) {
                String selectedCourseID = course_idCom.getSelectionModel().getSelectedItem();
                String selecteSemester = semesterCom.getSelectionModel().getSelectedItem();
                String selecteSec_Id = sec_idCom.getSelectionModel().getSelectedItem();
                ObservableList<String> lectureIds = FXCollections.observableList(db.getLectureId(selectedCourseID, selectedYear, selecteSemester ,selecteSec_Id));
                lecture_idCom.setItems(lectureIds);
            }
        }
    }


    @FXML
    void view(ActionEvent event) {
        if (course_idCom.getValue() == null || sec_idCom.getValue() == null||lecture_idCom.getValue() == null || yearCom.getValue() == null || semesterCom.getValue()== null){
            massage.setText("Enter all data ");
            massage.setTextFill(Color.RED);
            massage.setVisible(true);
        } else {
            massage.setText("");
            massage.setVisible(false);
            sheet.setItems(FXCollections.observableArrayList(db.attendanceSheet(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue())));
            if (Integer.parseInt(db.attendance_percentage(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue()))> 25){
                attendance_percentage.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                attendance_percentage.setTextFill(Color.GREEN);
                attendance_percentage.setText(db.attendance_percentage(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue()) + "%");
            }else {
                attendance_percentage.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                attendance_percentage.setTextFill(Color.RED);
                attendance_percentage.setText(db.attendance_percentage(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue())+ "%");
            }
            student_count.setText(db.student_count(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue()));
            attendance_count.setText(db.attendance_count(lecture_idCom.getValue(),course_idCom.getValue() ,yearCom.getValue(),semesterCom.getValue(), sec_idCom.getValue()));
        }

    }
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.REPORT_FXML);
    }
}

