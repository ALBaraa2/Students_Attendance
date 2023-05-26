package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.mindrot.jbcrypt.BCrypt;
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
        if(db.verifyPassword(passwoed.getText() , db.getPassword(email.getText())))
        if (db.getEmail(email.getText())){
            if (db.isAdmin(email.getText())) {
                nav.navigateTo(root, nav.ADMIN_FXML);
            }else
                nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
        }else
            faild.setVisible(true);

    }
}