package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserAccount implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private ComboBox<String> UT;

    @FXML
    private Button bId;

    @FXML
    private TextField email;

    @FXML
    private Label emailL;

    @FXML
    private TextField name;

    @FXML
    private Label nameL;

    @FXML
    private PasswordField password;

    @FXML
    private Label passwordL;

    @FXML
    private Button rId;

    @FXML
    private Label userTypeL;

    @FXML
    private Label massege;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxes();
    }
    private void setComboBoxes() {
        ObservableList<String> items = FXCollections.observableArrayList("Admin", "Teach assistant");
        UT.setItems(items);
    }
    @FXML
    void backToAdmin(ActionEvent event) {
        nav.navigateTo(root, nav.ADMIN_FXML);
    }

    @FXML
    void regisert(ActionEvent event) {
        if (!db.isPre_Registered(email.getText())){
            if (!name.getText().equals("") && !email.getText().equals("") && !password.getText().equals("")){
                if (UT.getValue().toString().equals("Admin") || UT.getValue().toString().equals("Teach assistant")) {
                    String usertype;
                    if (UT.getValue().toString().equals("Admin")){
                        usertype = "admin";
                    } else
                        usertype = "teach assistant";
                    if (db.creatUser(name.getText(), email.getText(), password.getText(), usertype)){
                        massege.setText("Registration Successful");
                        massege.setVisible(true);
                        reset();
                    } else {
                        massege.setText("Registration failed");
                        massege.setVisible(true);
                    }
                } else {
                    massege.setText("Select the user type");
                    massege.setVisible(true);
                }
            } else {
                massege.setText("Complete data registration");
                massege.setVisible(true);
            }
        } else {
            massege.setText("This email is used!");
            massege.setVisible(true);
        }
    }

    void reset(){
        name.setText("");
        email.setText("");
        password.setText("");
        UT.setValue("Select user type");
    }
}
