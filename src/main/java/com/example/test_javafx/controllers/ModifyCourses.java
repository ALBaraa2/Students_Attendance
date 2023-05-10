package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCourses implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label modifyMassege;

    @FXML
    private TextField courseLocation;

    @FXML
    private TextField courseName;

    @FXML
    private Label course_name;

    @FXML
    private TextField instructorName;

    @FXML
    private ComboBox selectCourse_id;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        selectCourse_id.setItems(ids);
    }

    @FXML
    void backToCourse(ActionEvent event) {
        nav.navigateTo(root, nav.COURSE_FXML);
    }

    @FXML
    void doneModify(ActionEvent event) {
        if (db.modifyCourse(selectCourse_id.getValue().toString(), courseName.getText(), instructorName.getText(), courseLocation.getText())){
            modifyMassege.setText("Updated successfully");
            modifyMassege.setVisible(true);
        }else {
            modifyMassege.setText("Update failed!");
            modifyMassege.setVisible(true);
        }
    }

    @FXML
    public void ids() {
        if (!selectCourse_id.equals("Select course")) {
            course_name.setText(db.getcourseName(selectCourse_id.getValue().toString()));
        }
    }

}

