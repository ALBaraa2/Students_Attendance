package com.example.test_javafx.controllers;
import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentRegistration  implements Initializable {

    Navigation nav = new Navigation();
    public void initialize(URL url, ResourceBundle rb) {
        sGender();
    }
    DBModel db = DBModel.getModel();

    @FXML
    private TextField Scity;

    @FXML
    private ComboBox<String> Sgender;

    @FXML
    private TextField Sid;

    @FXML
    private TextField Sname;

    @FXML
    private TextField Sph1;

    @FXML
    private TextField Sph11;

    @FXML
    private TextField Sph12;

    @FXML
    private TextField Sstreet;

    @FXML
    private Button done;

    @FXML
    private Button back;
    @FXML
    private Label xz;



    private void sGender(){
        ObservableList<String> genderOptions = FXCollections.observableArrayList("male", "female");
        Sgender.setItems(genderOptions);
        Sgender.setValue("Gender");
    }
    private boolean textField2Visible = false;
    private boolean textField3Visible = false;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (!textField2Visible) {
            Sph11.setVisible(true);
            textField2Visible = true;
        } else if (!textField3Visible) {
            Sph12.setVisible(true);
            textField3Visible = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("You have exceeded the limit allowed to add student_phone");
            alert.showAndWait();
        }
    }

    public boolean validateStudentId(String studentId) {
        if (studentId.matches("\\d{9}")) {
            return true;
        } else {
            return false;
        }
    }


    @FXML
    void done(ActionEvent event) {
        String studentId = Sid.getText();
        String studentName = Sname.getText();
        String street = Sstreet.getText();
        String city = Scity.getText();
        String gender = Sgender.getValue();
        String num1 = Sph1.getText().trim();
        String num2 = Sph11.getText().trim();
        String num3 = Sph12.getText().trim();

        


        if (studentId.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Please enter your ID");
        } else if (studentName.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Please enter your name");
        } else if (street.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Please enter your address (street)");
        } else if (city.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Please enter your address (city)");
        } else if (gender == null || gender.isEmpty() || gender.equals("Gender")) {
            xz.setTextFill(Color.RED);
            xz.setText("Please select your gender");
        }else if(num1.isEmpty() && num2.isEmpty() && num3.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Please select your phone");
        }else {
            if(validateStudentId(studentId)){
                if(db.checkStudentExists(studentId)){
                    xz.setTextFill(Color.RED);
                    xz.setText("This id is already exist");
                }else{
                    if(!num1.isEmpty() && num2.isEmpty() && num3.isEmpty()) {
                        db.addStudent(studentId,studentName,street,city,gender);
                        db.insertPhone(studentId,num1);
                        xz.setTextFill(Color.GREEN);
                        xz.setText("successfully registered");
                    }else if (!num1.isEmpty() && !num2.isEmpty() && num3.isEmpty()){
                        db.addStudent(studentId,studentName,street,city,gender);
                        db.insertPhone2(studentId,num1,num2);
                        xz.setTextFill(Color.GREEN);
                        xz.setText("successfully registered");
                    } else if (!num1.isEmpty() && !num2.isEmpty() && !num3.isEmpty()) {
                        db.addStudent(studentId,studentName,street,city,gender);
                        db.insertPhone3(studentId,num1,num2,num3);
                        xz.setTextFill(Color.GREEN);
                        xz.setText("successfully registered");
                    }
                }
            }else{
                xz.setTextFill(Color.RED);
                xz.setText("Enter the ID correctly ");
            }
        }
    }
    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(back, nav.STUDENT_FXML);
    }





}
