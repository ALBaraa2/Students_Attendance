package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private AnchorPane zzz;

    @FXML
    private Button Login;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Label error;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void login(){
        String Email = email.getText();
        String Password = password.getText();
        if (db.getEmailPassword(Email, Password)){
            nav.navigateTo(zzz, nav.START_CONTROLLER_FXML);
        }else
            error.setText("Email or Password incorrect!");
    }

}


