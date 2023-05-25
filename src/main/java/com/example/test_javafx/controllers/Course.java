package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.Courses;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ButtonBar.ButtonData;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class Course implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Button view;

    @FXML
    private TableColumn<Courses, String> course_id;

    @FXML
    private TableColumn<Courses, String> course_location;

    @FXML
    private TableColumn<Courses, String> course_name;

    @FXML
    private TableColumn<Course, String> instructor_name;

    @FXML
    private TableView<Courses> courses;

    @FXML
    private ComboBox<String> semester;

    @FXML
    private ComboBox<String> year;

    @FXML
    private Label massege;


    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        instructor_name.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
        course_location.setCellValueFactory(new PropertyValueFactory<>("course_location"));
        course_name.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        doubleClick(courses);
        setComboBoxes();
    }

    private void setComboBoxes() {
        ObservableList<String> years = FXCollections.observableList(db.getYears());
        year.setItems(years);
        year.setOnAction(this::handleSAction);
    }

    @FXML
    private void handleSAction(ActionEvent event) {
        if (year.getValue() != null) {
            String selectedYear = year.getSelectionModel().getSelectedItem();
            ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedYear));
            semester.setItems(semesters);
        }
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
    void viewCourses(ActionEvent event) {
        if (year.getValue() != null && semester.getValue() != null) {
            massege.setVisible(false);
            courses.setItems(FXCollections.observableArrayList(db.getCourses(year.getValue(), semester.getValue())));
        } else {
            massege.setText("Select Year and Semester");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    public void doubleClick(TableView<Courses> courses) {
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        courses.setRowFactory(tv -> {
            TableRow<Courses> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Courses rowData = row.getItem();
                    Optional<ButtonType> result = showAlert("Are You sure delete " + rowData.getCourse_id());
                    if (result.isPresent()) {
                        if (result.get() == ButtonType.OK) {
                            db.deleteCourse(rowData.getCourse_id());
                            nav.navigateTo(root, nav.COURSE_FXML);
                            view.setOnAction(this::viewCourses);
                        } else if (result.get() == buttonCancel) {
                            nav.navigateTo(root, nav.COURSE_FXML);
                            courses.setItems(FXCollections.observableArrayList(db.getCourses(year.getValue(), semester.getValue())));
                        }
                    }
                }
            });
            return row;
        });
    }

    public Optional<ButtonType> showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(buttonCancel);
        return alert.showAndWait();
    }
}
