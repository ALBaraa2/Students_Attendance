package com.example.test_javafx.controllers;
import com.example.test_javafx.models.CmboBoxAutoComplete;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyCourses   implements  Initializable {

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

        selectCourse_id.setValue("Select course");
        setComboBoxes();
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        selectCourse_id.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(selectCourse_id , ids );
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
        if (!CL.isSelected()) {
            courseLocation.setVisible(false);
            LCL.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void checkCN(ActionEvent event) {
        courseName.setVisible(true);
        LCN.setVisible(true);
        Done.setVisible(true);
        if (!CN.isSelected()) {
            courseName.setVisible(false);
            LCN.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void checkIN(ActionEvent event) {
        instructorName.setVisible(true);
        LIN.setVisible(true);
        Done.setVisible(true);
        if (!IN.isSelected()) {
            instructorName.setVisible(false);
            LIN.setVisible(false);
        }
        if (!IN.isSelected() && !CN.isSelected() && !CL.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void doneModify(ActionEvent event) {
        if (selectCourse_id.equals("Select course")) {
            modifyMassege.setText("Please select course id!");
            modifyMassege.setVisible(true);
        } else {
            modifyMassege.setVisible(false);
            if (CL.isSelected() && CN.isSelected() && IN.isSelected()) {
                if (courseName.getText().equals("") || courseLocation.getText().equals("") || instructorName.getText().equals("")) {
                    modifyMassege.setText("Enter Values!");
                    modifyMassege.setTextFill(Color.RED);
                    modifyMassege.setVisible(true);
                } else {
                    if (db.modifyCourseName(selectCourse_id.getValue().toString(), courseName.getText()) &&
                            db.modifyCourseLocation(selectCourse_id.getValue().toString(), courseLocation.getText()) &&
                            db.modifyInstructorName(selectCourse_id.getValue().toString(), instructorName.getText())) {
                        modifyMassege.setText("Modified successfully");
                        reset();
                    } else {
                        modifyMassege.setText("Modification failed!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
            } else {
                if (CN.isSelected() && (!CL.isSelected() && !IN.isSelected())) {
                    if (!courseName.getText().equals("")) {
                        if (db.modifyCourseName(selectCourse_id.getValue().toString(), courseName.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
                if (CN.isSelected() && CL.isSelected() && !IN.isSelected()) {
                    if (!courseName.getText().equals("") && !courseLocation.getText().equals("")) {
                        if (db.modifyCourseName(selectCourse_id.getValue().toString(), courseName.getText()) &&
                                db.modifyCourseLocation(selectCourse_id.getValue().toString(), courseLocation.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
                if (CN.isSelected() && !CL.isSelected() && IN.isSelected()) {
                    if (!courseName.getText().equals("") && !instructorName.getText().equals("")) {
                        if (db.modifyCourseName(selectCourse_id.getValue().toString(), courseName.getText()) &&
                                db.modifyInstructorName(selectCourse_id.getValue().toString(), instructorName.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
                if (!CN.isSelected() && CL.isSelected() && IN.isSelected()) {
                    if (!courseLocation.getText().equals("") && !instructorName.getText().equals("")) {
                        if (db.modifyCourseLocation(selectCourse_id.getValue().toString(), courseLocation.getText()) &&
                                db.modifyInstructorName(selectCourse_id.getValue().toString(), instructorName.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
                if ((!CN.isSelected() && !CL.isSelected()) && IN.isSelected()) {
                    if (!instructorName.getText().equals("")) {
                        if (db.modifyInstructorName(selectCourse_id.getValue().toString(), instructorName.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
                if (CL.isSelected() && (!CN.isSelected() && !IN.isSelected())) {
                    if (!courseLocation.getText().equals("")) {
                        if (db.modifyCourseLocation(selectCourse_id.getValue().toString(), courseLocation.getText())) {
                            modifyMassege.setText("Modified successfully");
                            reset();
                        } else {
                            modifyMassege.setText("Modification failed!");
                            modifyMassege.setTextFill(Color.RED);
                            modifyMassege.setVisible(true);
                        }
                    } else {
                        modifyMassege.setText("Enter Values!");
                        modifyMassege.setTextFill(Color.RED);
                        modifyMassege.setVisible(true);
                    }
                }
            }
        }
    }

    private void reset() {
        modifyMassege.setTextFill(Color.GREEN);
        modifyMassege.setVisible(true);
        courseName.setText("");
        courseLocation.setText("");
        instructorName.setText("");
        courseName.setVisible(false);
        courseLocation.setVisible(false);
        instructorName.setVisible(false);
        LCN.setVisible(false);
        LIN.setVisible(false);
        LCL.setVisible(false);
        Done.setVisible(false);
        CN.setSelected(false);
        CL.setSelected(false);
        IN.setSelected(false);
        selectCourse_id.setValue("Select Course_id");
    }

    @FXML
    public void ids() {
        if (!selectCourse_id.equals("Select course")) {
            course_name.setText(db.getcourseName(selectCourse_id.getValue().toString()));
        }
    }
}