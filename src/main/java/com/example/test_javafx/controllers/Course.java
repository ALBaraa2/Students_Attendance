package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.Courses;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Course implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<Courses, String> course_id;

    @FXML
    private TableColumn<Courses, String> course_location;

    @FXML
    private TableColumn<Courses, String> course_name;

    @FXML
    private TableColumn<Courses, String> year;

    @FXML
    private TableColumn<Course, String> instructor_name;

    @FXML
    private TableView<Courses> courses;


    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        instructor_name.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
        course_location.setCellValueFactory(new PropertyValueFactory<>("course_location"));
        course_name.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
    }

    @FXML
    void backFromCourse(ActionEvent event) {
        nav.navigateTo(root, nav.ADMIN_FXML);
    }

    @FXML
    void insertPage(ActionEvent event) {
        nav.navigateTo(root, nav.INSERT_COURSE_FXML);
    }

    @FXML
    void modifyPage(ActionEvent event) {
        nav.navigateTo(root, nav.MODIFY_COURSE_FXML);
    }

    @FXML
    void delete(ActionEvent event) {
    }

    @FXML
    void viewCourses(ActionEvent event) {
        courses.setItems(FXCollections.observableArrayList(db.getCourses()));
    }

//    private void delete(){
//        courses.setRowFactory(tv -> {
//            TableRow<Courses> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//                    Courses rowData = row.getItem();
//                    db.deleteCourse(rowData.getCourse_id());
//                }
//            });
//            return row ;
//        });
//    }
}
