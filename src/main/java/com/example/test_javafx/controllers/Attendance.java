package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.AttendanceSheet;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import com.example.test_javafx.models.SharedData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
    private ComboBox<String> student;

    @FXML
    private TableColumn<AttendanceSheet, String> lecture_title;

    @FXML
    private TableView<AttendanceSheet> table;

    @FXML
    private Button xlx;

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
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs(email));
        courseID.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseID, ids);
        courseID.setOnAction(this::setComboBoxesSec_id);
    }
    private void setComboBoxesSec_id(ActionEvent event1) {
        System.out.println(year +"\n"+semester);
        String course_id = courseID.getSelectionModel().getSelectedItem();
        ObservableList<String> sec_ids = FXCollections.observableList(db.getSecIds(course_id,
                Integer.parseInt(year), semester));
        sec_id.setItems(sec_ids);

        EventHandler<ActionEvent> comboEvent = (ActionEvent event) -> {
            setComboBoxesLecturename(event);
            setComboBoxesStudent(event);
        };

        sec_id.setOnAction(comboEvent);
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


    public void xlsx() {
        xlx.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose File");

            // تحديد نوع الملفات المسموح بها
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.xlsx")

            );
            Stage fileChooserStage = new Stage();
            fileChooserStage.setTitle("File Chooser");


            // الحصول على الملف المختار عند النقر على زر "Open"
            java.io.File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
            if (selectedFile != null) {
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            } else {
                System.out.println("No file selected.");
            }

        });

    }
}