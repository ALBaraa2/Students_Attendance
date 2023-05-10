package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GGG implements Initializable {


    @FXML
    public BorderPane root;

    @FXML
    public TextField email;
    @FXML
    public TextField passwoed;

    @FXML
    public Button log;


    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void ass() {
        nav.navigateTo(root, nav.START_CONTROLLER_FXML);

    }
}