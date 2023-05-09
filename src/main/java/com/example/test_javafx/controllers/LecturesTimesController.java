package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.LectureTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LecturesTimesController implements Initializable {

    @FXML
    public TableView<LectureTime> time_table;
    @FXML
    public TableColumn<LectureTime, String> course_id;
    @FXML
    public TableColumn<LectureTime, String> course_title;
    @FXML
    public TableColumn<LectureTime, String> building;
    @FXML
    public TableColumn<LectureTime, String> room_number;
    @FXML
    public TableColumn<LectureTime, String> time_slot;
    @FXML
    public SplitPane root;
    @FXML
    public TextField id;
    @FXML
    public TextField name;
    @FXML
    public TextField dept;
    @FXML
    public ComboBox<String> year;
    @FXML
    public ComboBox<String> sem;
    @FXML
    public CheckBox student;
    @FXML
    public Button close;
    public Button view;
    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //make sure the property value factory should be exactly same as the getStudentId from your model class
        course_id.setCellValueFactory(new PropertyValueFactory<>("Course_id"));
        course_title.setCellValueFactory(new PropertyValueFactory<>("Course_title"));
        building.setCellValueFactory(new PropertyValueFactory<>("Building"));
        room_number.setCellValueFactory(new PropertyValueFactory<>("Room_number"));
        time_slot.setCellValueFactory(new PropertyValueFactory<>("Time_slot"));
    }

    public void close() {
        nav.navigateTo(root, nav.MAIN_FXML);
    }

    @FXML
    public void onIDEnter() {
        if (!id.getText().equals("")) {
            if (student.isSelected()) {
                name.setText(db.getStdName(id.getText()));
                dept.setText(db.getStdDept(id.getText()));
                ObservableList<String> years = FXCollections.observableList(db.getStdYears(id.getText()));
                year.setItems(years);
            } else {
                name.setText(db.getInstructorName(id.getText()));
                dept.setText(db.getInstructorDept(id.getText()));
                ObservableList<String> years = FXCollections.observableList(db.getInstructorYears(id.getText()));
                year.setItems(years);
            }
        }
    }
    //

    public void upCombSems() {

        if (year.getValue() != null) {
            if (student.isSelected()) {
                ObservableList<String> sems = FXCollections.observableList(db.getStdSems(id.getText(),
                        Integer.parseInt(year.getValue())));
                sem.setItems(sems);
            } else {
                ObservableList<String> sems = FXCollections.observableList(db.getInstructorSems(id.getText(),
                        Integer.parseInt(year.getValue())));
                sem.setItems(sems);
            }
        } else sem.setItems(null);
    }

    public void viewTimes() {
        if (id.getText() != null && sem.getValue() != (null)
                && year.getValue() != null) {
            if (student.isSelected())
                time_table.setItems(FXCollections.observableArrayList(
                        db.getStdLectures(id.getText(), sem.getValue(),
                                Integer.parseInt(year.getValue()))));
            else
                time_table.setItems(FXCollections.observableArrayList(
                        db.getInstructorLectures(id.getText(), sem.getValue(),
                                Integer.parseInt(year.getValue()))));
        } else System.err.println("Error! fill fields");
    }
}
