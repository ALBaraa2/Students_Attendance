package com.example.test_javafx.controllers;
import com.example.test_javafx.Navigation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class TAEnrollmentCourse implements Initializable {


        @FXML
        private ComboBox<String> select;
        @FXML
        private BorderPane bord;
        @FXML
        private TextField TAname;

        @FXML
        private Button close;

        @FXML
        private Button done;
    Navigation nav = new Navigation();

    public void initialize(URL url, ResourceBundle rb) {
         select.setItems(FXCollections.observableArrayList("hamza","feras almanik","baraa alchrmoot"));
    }

    public void close() {
//        nav.navigateTo(bord, nav.TeachingAssistant_FXML);

    }
//    public void done() {
//        nav.navigateTo(bord, nav.TeachingAssistant_FXML);
//    }
}



