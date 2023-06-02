package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.SharedData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.mindrot.jbcrypt.BCrypt;
import java.net.URL;
import java.util.ResourceBundle;

public class Login {

    @FXML
    public BorderPane root;

    @FXML
    private Label faild;
    
    @FXML
    public TextField email;
    
    @FXML
    public TextField password;

    @FXML
    public Button log;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    

    public void ass() {
        if (db.getEmail(email.getText())) {
            if (db.verifyPassword(password.getText(), db.getPassword(email.getText()))) {
                    if (db.isAdmin(email.getText())) {
                        nav.navigateTo(root, nav.ADMIN_FXML);
                    } else {
                        SharedData.getInstance().setEmail(email.getText());
                        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
                    }
                } else {
                    faild.setText("wrong password");
                    faild.setVisible(true);
                }
        }else{
            faild.setText("wrong email");
            faild.setVisible(true);
        }
    }
}