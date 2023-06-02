package com.example.test_javafx;

import com.example.test_javafx.models.DBModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/taenrollmentcourse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("University");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/uni.jpg"))));
        stage.setScene(scene);
        stage.show();
    }
}
