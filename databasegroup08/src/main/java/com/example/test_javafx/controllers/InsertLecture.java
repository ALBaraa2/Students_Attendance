package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.SharedData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InsertLecture implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label CIL;

    @FXML
    private Label SIL;

    @FXML
    private Label SL;

    @FXML
    private Label YL;

    @FXML
    private DatePicker date;

    @FXML
    private Spinner<Integer> houre;

    @FXML
    private Spinner<Integer> minute;

    @FXML
    private TextField location;

    @FXML
    private Label massege;

    @FXML
    private TextField title;

    String course_id = SharedData.getInstance().getCourse_id();
    String year = SharedData.getInstance().getYear();
    String semester = SharedData.getInstance().getSemester();
    String sec_id = SharedData.getInstance().getSec_id();
    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {
        CIL.setText(course_id);
        YL.setText(String.valueOf(year));
        SL.setText(semester);
        SIL.setText(String.valueOf(sec_id));
        minute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        houre.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
        minute.setEditable(true);
        houre.setEditable(true);
    }

    @FXML
    void backToLecture(ActionEvent event) {
        nav.navigateTo(root, nav.LECTURES_FXML);
    }

    @FXML
    void done(ActionEvent event) {
        if (!title.getText().equals("") && date.getValue() != null && houre.getValue() != null) {
            String l = location.getText();
            String time = houre.getValue().toString() + ":" + minute.getValue().toString() + ":00";
            LocalDate localDate = date.getValue();
            String date = String.valueOf(localDate);
            if (l.equals("")){
                l = db.getLocationFromSection(course_id,year,semester,sec_id);
            }
            if (db.insertLecture(title.getText(), date, time, l,
                    course_id, year, semester, sec_id)) {
                massege.setText("Registration Successful");
                massege.setTextFill(Color.GREEN);
                massege.setVisible(true);
            } else {
                massege.setText("Registration failed");
                massege.setTextFill(Color.RED);
                massege.setVisible(true);
            }
        } else {
            massege.setText("Complete the data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }
}