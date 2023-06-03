package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
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
        //وضع البيانات داخل الاعمدة في tableview
        semester.setItems(FXCollections.observableArrayList("Fall", "Spring", "Summer", "Winter"));
        //تفعيل ميثودsetComboBoxes عند تشغيل البرنامج
        setComboBoxes();
    }

    //  وضع جميع الكورسات من جدول الcourses قي ComboBoxes
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        course_id.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(course_id, ids);
    }

    //الرجوع الى صفحة الcourse
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.COURSE_FXML);
    }

    //في حال عدم وجود section عند الضغط على الزر يتم اضافة section اعتمادا على القيم التي يتم اختيارها من ComboBoxes
    @FXML
    void done(ActionEvent event) {
        if (course_id.getValue() != null && semester.getValue() != null && year.getText() != null){
            String l = location.getText();
            if (l.equals("")){
                l = db.getLocationFromCourse(course_id.getValue());
            }
            if (db.addSection(course_id.getValue(), l, year.getText(), semester.getValue())){
                if (db.isAssistedByAssistant(course_id.getValue(), year.getText(), semester.getValue())){
                    int assistant_id = Integer.parseInt(db.getAssistantIdAndSecID(course_id.getValue(),year.getText(),semester.getValue()).get(0));
                    int sec_id = Integer.parseInt(db.getAssistantIdAndSecID(course_id.getValue(),year.getText(),semester.getValue()).get(1)) + 1;
                    db.enrollNewSecToAssistant(course_id.getValue(),year.getText(),semester.getValue(),assistant_id,sec_id);
                }
                masssege.setText("Successful");
                masssege.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                masssege.setTextFill(Color.GREEN);
                masssege.setVisible(true);
            } else {
                masssege.setText("Failed");
                masssege.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                masssege.setTextFill(Color.RED);
                masssege.setVisible(true);
            }
        } else {
            masssege.setText("Complete data");
            masssege.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            masssege.setTextFill(Color.RED);
            masssege.setVisible(true);
        }
    }
}
