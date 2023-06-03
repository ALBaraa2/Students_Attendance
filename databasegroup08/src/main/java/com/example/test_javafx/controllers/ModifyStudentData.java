package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyStudentData  implements Initializable {

    Navigation nav = new Navigation();
    public void initialize(URL url, ResourceBundle rb) {
        setStudent_Id();
        sGender();
    }
    DBModel db = DBModel.getModel();


    @FXML
    private Button back;

    @FXML
    private Button dC;

    @FXML
    private Button dG;

    @FXML
    private Button dN;

    @FXML
    private Button dPh;

    @FXML
    private Button dS;

    @FXML
    private ComboBox<String> s_ge;

    @FXML
    private ComboBox<String> s_id;

    @FXML
    private ComboBox<String> s_ph;
    @FXML
    private Label xz;

    @FXML
    private TextField tC;

    @FXML
    private TextField tN;

    @FXML
    private TextField tPh;

    @FXML
    private TextField tS;
    @FXML
    private void setStudent_Id(){
        ObservableList<String> ids = FXCollections.observableArrayList(db.getAllStudentIds());
        s_id.setItems(ids);
        s_id.setPromptText("S_ID");
        CmboBoxAutoComplete.cmboBoxAutoComplete(s_id , ids);
        s_id.setOnAction(this::setStudent_PhoneAction);
    }

    @FXML
    private void setStudent_PhoneAction(ActionEvent event) {
        if (s_id.getValue() != null) {
            String selectedID = s_id.getSelectionModel().getSelectedItem();
            ObservableList<String> phones = FXCollections.observableArrayList(db.getStudentPhoneNumbers(selectedID));
            s_ph.setItems(phones);
            CmboBoxAutoComplete.cmboBoxAutoComplete(s_ph , phones);
        }
    }

    @FXML
    private void sGender(){
        ObservableList<String> genderOptions = FXCollections.observableArrayList("male", "female");
        s_ge.setItems(genderOptions);

    }

    @FXML
    void dN(ActionEvent event) {
        String selectedID = s_id.getSelectionModel().getSelectedItem();
        String newName = tN.getText();
        if (selectedID == null || selectedID.equals("S_ID")) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter your ID!!");
        } else if(newName.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter new Name!!");
        } else if (db.updateStudentName(selectedID, newName)) {
            xz.setTextFill(Color.GREEN);
            xz.setText("successfully Update");
        } else {
            xz.setText("There was an error, try again!!");
        }
    }

    @FXML
    void dPh(ActionEvent event) {
        String selectedID = s_id.getSelectionModel().getSelectedItem();
        String s_Opho = s_ph.getValue();
        String selectedNewPhone = tPh.getText().trim();
        if (selectedID == null || selectedID.equals("S_ID")) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter your ID!!");
        } else if (s_Opho == null || s_Opho.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Select old Phone!!");
        } else if (selectedNewPhone.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter new Phone!!");
        } else if (selectedNewPhone.length() != 10) {
            xz.setTextFill(Color.RED);
            xz.setText("You must enter ten digits!!");
        } else if (db.updateStudentPhoneNumber(selectedID, s_Opho, selectedNewPhone)) {
            xz.setTextFill(Color.GREEN);
            xz.setText("Successfully updated");
        } else {
            xz.setTextFill(Color.RED);
            xz.setText("There was an error, try again!!");
        }
    }

    @FXML
    void dC(ActionEvent event) {
        String selectedID = s_id.getSelectionModel().getSelectedItem();
        String newCity = tC.getText();

        if (selectedID == null || selectedID.equals("S_ID")) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter your ID!!");
        } else if(newCity.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter new City!!");
        }else if(db.updateStudentCity(selectedID,newCity)){
            xz.setTextFill(Color.GREEN);
            xz.setText("Successfully updated");
        }else {
            xz.setTextFill(Color.RED);
            xz.setText("There was an error, try again!!");
        }
    }

    @FXML
    void dS(ActionEvent event) {
        String selectedID = s_id.getSelectionModel().getSelectedItem();
        String newStreet = tS.getText();
        if (selectedID == null || selectedID.equals("S_ID")) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter your ID!!");
        } else if(newStreet.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter new Street!!");
        }else if(db.updateStudentStreet(selectedID,newStreet)){
            xz.setTextFill(Color.GREEN);
            xz.setText("Successfully updated");
        }else {
            xz.setTextFill(Color.RED);
            xz.setText("There was an error, try again!!");
        }
    }

    @FXML
    void dG(ActionEvent event) {
        String selectedID = s_id.getSelectionModel().getSelectedItem();
        String s_G = s_ge.getValue();
        if (selectedID == null || selectedID.equals("S_ID")) {
            xz.setTextFill(Color.RED);
            xz.setText("Enter your ID!!");
        } else if (s_G == null || s_G.isEmpty()) {
            xz.setTextFill(Color.RED);
            xz.setText("Select new Gender!!");
        }else if(db.updateStudentGender(selectedID,s_G)){
            xz.setTextFill(Color.GREEN);
            xz.setText("Successfully updated");
        }else {
            xz.setTextFill(Color.RED);
            xz.setText("There was an error, try again!!");
        }
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(back, nav.STUDENT_FXML);
    }
}
