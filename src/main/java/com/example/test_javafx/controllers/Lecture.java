package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Lecture implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<Lectures, String> course_id;

    @FXML
    private TableColumn<Lectures, String> lecture_date;

    @FXML
    private TableColumn<Lectures, String> lecture_id;

    @FXML
    private TableColumn<Lectures, String> lecture_location;

    @FXML
    private TableColumn<Lectures, String> lecture_time;

    @FXML
    private TableColumn<Lectures, String> lecture_title;

    @FXML
    private TableView<Lectures> lectures;

    @FXML
    public ComboBox<String> CIcom;

    @FXML
    public ComboBox<String> SIcom;


    @FXML
    private Label massege1;

    @FXML
    public ComboBox<String> Scom;

    @FXML
    public ComboBox<String> Ycom;


    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course_id.setCellValueFactory(new PropertyValueFactory<>("Course_id"));
        lecture_id.setCellValueFactory(new PropertyValueFactory<>("lecture_id"));
        lecture_title.setCellValueFactory(new PropertyValueFactory<>("lecture_title"));
        lecture_time.setCellValueFactory(new PropertyValueFactory<>("lecture_time"));
        lecture_date.setCellValueFactory(new PropertyValueFactory<>("lecture_date"));
        lecture_location.setCellValueFactory(new PropertyValueFactory<>("Hall"));
        setComboBoxes();
        doubleClick(lectures);
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
        CIcom.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(CIcom , ids);
        CIcom.setOnAction(this::handleCIcomAction);
    }

    @FXML
    private void handleCIcomAction(ActionEvent event) {
        if (CIcom.getValue() != null) {
            String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
            ObservableList<String> year = FXCollections.observableList(db.getYears(selectedCourseID));
            Ycom.setItems(year);
            Ycom.setOnAction(this::handleYcomAction);
        }
    }

    private void handleYcomAction(ActionEvent event) {
        int selectedYear;
        if (Ycom.getValue() != null) {
            selectedYear = Integer.parseInt(Ycom.getSelectionModel().getSelectedItem());
            if (CIcom.getValue() != null) {
                String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
                ObservableList<String> semesters = FXCollections.observableList(db.getSemesters(selectedCourseID, selectedYear));
                Scom.setItems(semesters);
                Scom.setOnAction(this::handleScomAction);
            }
        }
    }

    private void handleScomAction(ActionEvent event) {
        int selectedYear;
        if (Ycom.getValue() != null) {
        selectedYear = Integer.parseInt(Ycom.getSelectionModel().getSelectedItem());
        if (CIcom.getValue() != null && Scom.getValue() != null) {
            String selectedCourseID = CIcom.getSelectionModel().getSelectedItem();
            String selecteSemester = Scom.getSelectionModel().getSelectedItem();
            ObservableList<String> SecIds = FXCollections.observableList(db.getSecIds(selectedCourseID, selectedYear, selecteSemester));
            SIcom.setItems(SecIds);
        }
    }
}



    @FXML
    void backToTeachassistant(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }

    @FXML
    void insert(ActionEvent event) {
        if (CIcom.getValue() != null && Ycom.getValue() != null && Scom.getValue() != null && SIcom.getValue() != null) {
            SharedData.getInstance().setCourse_id(CIcom.getValue());
            SharedData.getInstance().setYear(Ycom.getValue());
            SharedData.getInstance().setSemester(Scom.getValue());
            SharedData.getInstance().setSec_id(SIcom.getValue());
            nav.navigateTo(root, nav.INSERT_LECTURSE_FXML);
        } else {
            massege1.setText("Choose the course data you want to enter the lecture on");
            massege1.setTextFill(Color.RED);
            massege1.setVisible(true);
        }
    }

    @FXML
    void modify(ActionEvent event) {
        if (CIcom.getValue() != null && Ycom.getValue() != null && Scom.getValue() != null && SIcom.getValue() != null) {
            SharedData.getInstance().setCourse_id(CIcom.getValue());
            SharedData.getInstance().setYear(Ycom.getValue());
            SharedData.getInstance().setSemester(Scom.getValue());
            SharedData.getInstance().setSec_id(SIcom.getValue());
            nav.navigateTo(root, nav.MODIFY_LECTURE_FXML);
        } else {
            massege1.setText("Choose the course data you want to modify");
            massege1.setTextFill(Color.RED);
            massege1.setVisible(true);
        }
    }

    @FXML
    void viewLectuers(ActionEvent event) {
        if (CIcom.getValue() == null || SIcom.getValue() == null){
            massege1.setText("Enter all data ");
            massege1.setTextFill(Color.RED);
            massege1.setVisible(true);
        } else {

            if ((db.getLectures(CIcom.getValue(), Integer.parseInt(Ycom.getValue()), Scom.getValue(), Integer.parseInt(SIcom.getValue()))).isEmpty()){
                massege1.setText("There is no lecture in this course yet!");
                massege1.setVisible(true);
            }else
                lectures.setItems(FXCollections.observableArrayList(db.getLectures(CIcom.getValue(), Integer.parseInt(Ycom.getValue()), Scom.getValue(), Integer.parseInt(SIcom.getValue()))));
        }
    }
    public void doubleClick(TableView<Lectures> Lectures) {
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Lectures.setRowFactory(tv -> {
            TableRow<Lectures> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Lectures rowData = row.getItem();
                    Optional<ButtonType> result = showAlert("Are You sure delete " + rowData.getLecture_title());
                    if (result.isPresent()) {
                        if (result.get() == ButtonType.OK) {
                            System.out.println(rowData.getLecture_id() +"" +Ycom.getValue()+"" +Scom.getValue() +"" +CIcom.getValue()+ "" +Integer.parseInt(SIcom.getValue()));
                            db.deleteLecture(rowData.getLecture_id() ,Integer.parseInt(Ycom.getValue()) ,Scom.getValue() ,CIcom.getValue(),Integer.parseInt(SIcom.getValue()));
                            nav.navigateTo(root, nav.LECTURES_FXML);
                        } else if (result.get() == buttonCancel) {
                            nav.navigateTo(root, nav.LECTURES_FXML);
                        }
                    }
                }
            });
            return row;
        });
    }
    public Optional<ButtonType> showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(buttonCancel);
        return alert.showAndWait();
    }

}
