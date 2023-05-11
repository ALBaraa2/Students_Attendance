package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    @FXML
    public BorderPane root;

    @FXML
    private Label faild;
    @FXML
    public TextField email;
    @FXML
    public TextField passwoed;

    @FXML
    public Button log;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void ass() {
        if (db.getEmailPassword(email.getText(), passwoed.getText())){
            nav.navigateTo(root, nav.START_CONTROLLER_FXML);
        }else
            faild.setVisible(true);

    }
}