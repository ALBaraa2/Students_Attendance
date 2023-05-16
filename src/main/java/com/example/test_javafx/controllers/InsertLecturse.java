package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertLecturse implements Initializable {


    @FXML
    private BorderPane root;

    @FXML
    private ComboBox<String> course_id;

    @FXML
    private DatePicker date;

    @FXML
    private TextField location;

    @FXML
    private TextField time;

    @FXML
    private TextField title;

    @FXML
    private Label massege;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxes();
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        course_id.setItems(ids);
    }

    @FXML
    void backToLecture(ActionEvent event) {
        nav.navigateTo(root, nav.LECTURES_FXML);
//        TimePicker timePicker = new TimePicker();
    }

    @FXML
    void done(ActionEvent event) {
//        if (course_id.getValue() != null){
//            if (title.getText() != null && date.getValue() != null && time.getText() != null && location.getText() != null){
//                if (){
//
//                } else {
//                    massege.setText("Registration failed");
//                    massege.setTextFill(Color.RED);
//                    massege.setVisible(true);
//                }
//            } else {
//                massege.setText("Complete the data");
//                massege.setTextFill(Color.RED);
//                massege.setVisible(true);
//            }
//        } else {
//            massege.setText("Select course id");
//            massege.setTextFill(Color.RED);
//            massege.setVisible(true);
//        }
//    }
    }
}
