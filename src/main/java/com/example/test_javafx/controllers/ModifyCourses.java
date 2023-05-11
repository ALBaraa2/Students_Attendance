package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCourses implements Initializable {

    @FXML
    private BorderPane root;

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

    @FXML
    private CheckBox CL;

    @FXML
    private CheckBox CN;

    @FXML
    private CheckBox IN;

    @FXML
    private Label LCL;

    @FXML
    private Label LCN;

    @FXML
    private Label LIN;

    @FXML
    private Button Done;

    @FXML
    private Label modifyMassege;


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
    void checkCL(ActionEvent event) {
        courseLocation.setVisible(true);
        LCL.setVisible(true);
        Done.setVisible(true);
        if (!CL.isSelected()){
            courseLocation.setVisible(false);
            LCL.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()){
            Done.setVisible(false);
        }
    }

    @FXML
    void checkCN(ActionEvent event) {
        courseName.setVisible(true);
        LCN.setVisible(true);
        Done.setVisible(true);
        if (!CN.isSelected()){
            courseName.setVisible(false);
            LCN.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()){
            Done.setVisible(false);
        }
    }

    @FXML
    void checkIN(ActionEvent event) {
        instructorName.setVisible(true);
        LIN.setVisible(true);
        Done.setVisible(true);
        if (!IN.isSelected()){
            instructorName.setVisible(false);
            LIN.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()){
            Done.setVisible(false);
        }
    }

    @FXML
    void doneModify(ActionEvent event) {
        if (CN.isSelected()){
            if (courseName.getText().length() > 0) {
                db.modifyCourseName(selectCourse_id.getValue().toString(), courseName.getText());
                modifyMassege.setText("Modified successfully");
                modifyMassege.setVisible(true);
            }else {

                modifyMassege.setVisible(true);
            }
        }
        if (CL.isSelected()){
            if (courseLocation.getText().length() > 0) {
                db.modifyCourseLocation(selectCourse_id.getValue().toString(), courseLocation.getText());
                modifyMassege.setText("Modified successfully");
                modifyMassege.setVisible(true);
            }else {

                modifyMassege.setVisible(true);
            }
        }
        if (IN.isSelected()){
            if (instructorName.getText().length() > 0) {
                db.modifyInstructorName(selectCourse_id.getValue().toString(), instructorName.getText());
                modifyMassege.setText("Modified successfully");
                modifyMassege.setVisible(true);
            }else {
                modifyMassege.setText("Enter Value!");
                modifyMassege.setVisible(true);
            }
        }
        if ((CN.isSelected() && courseName.getText().length() > 0) || (CL.isSelected() && courseLocation.getText().length() > 0) || (IN.isSelected() && instructorName.getText().length() > 0)){
            modifyMassege.setText("Modified successfully");
        }
    }

    @FXML
    public void ids() {
        if (!selectCourse_id.equals("Select course")) {
            course_name.setText(db.getcourseName(selectCourse_id.getValue().toString()));
        }
    }

}

