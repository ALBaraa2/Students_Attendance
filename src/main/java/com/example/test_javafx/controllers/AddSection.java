package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSection implements Initializable {

    @FXML
    private ComboBox<String> course_id;

    @FXML
    private Label masssege;

    @FXML
    private BorderPane root;

    @FXML
    private ComboBox<String> semester;

    @FXML
    private TextField year;

    @FXML
    private TextField location;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        semester.setItems(FXCollections.observableArrayList("Fall", "Spring", "Summer", "Winter"));
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        course_id.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(course_id, ids);
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.COURSE_FXML);
    }

    @FXML
    void done(ActionEvent event) {
        if (course_id.getValue() != null && semester.getValue() != null && year.getText() != null && location.getText() != null){
            if (!db.sectionIsExist(course_id.getValue(), year.getText(), semester.getValue())){
                if (db.addSection(course_id.getValue(), location.getText(), year.getText(), semester.getValue())){
                    masssege.setText("Successful");
                    masssege.setTextFill(Color.GREEN);
                    masssege.setVisible(true);
                } else {
                    masssege.setText("Failed");
                    masssege.setVisible(true);
                }
            } else {
                masssege.setText("This Section is exist");
                masssege.setVisible(true);
            }
        } else {
            masssege.setText("Complete data");
            masssege.setTextFill(Color.RED);
            masssege.setVisible(true);
        }
    }

}
