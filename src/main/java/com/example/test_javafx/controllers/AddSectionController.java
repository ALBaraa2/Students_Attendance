package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddSectionController implements Initializable {

    @FXML
    public AnchorPane rootPane;
    @FXML
    public ComboBox course_id;
    @FXML
    public ComboBox building;
    @FXML
    public ComboBox room;
    @FXML
    public ComboBox year;
    @FXML
    public ComboBox semester;
    @FXML
    public ComboBox time_slot;
    @FXML
    public Label course_name;
    @FXML
    public Label conflict_in_room;
    @FXML
    public Label notification;
    @FXML
    public Button btn_add;
    @FXML
    public Button btn_hide;
    @FXML
    public Button nav_back;
    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notification.setVisible(false);
        btn_hide.setVisible(false);

//        String image = Objects.requireNonNull(getClass().getResource("images/success.png")).toExternalForm();
//        btn_hide.setStyle("-fx-background-image: url('" + image + "'); " +
//                "-fx-background-position: center center;"
//                + "-fx-background-size: 32px 32px; -fx-background-repeat: no-repeat; ");

//        setComboBoxes();
    }

    private void setCombInitials() {
        building.setValue("Select building");
        course_id.setValue("Select course");
        room.setValue("Select room number");
        year.setValue("Select year");
        semester.setValue("Select semester");
        time_slot.setValue("Select time_slot");
    }

//    private void setComboBoxes() {
//        setCombInitials();
////        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs());
//        ObservableList<String> buildings = FXCollections.observableList(db.getBuildings());
//
//        List<Integer> years
//                = IntStream.rangeClosed(Integer.parseInt(Objects.requireNonNull("2000")),
//                        Integer.parseInt(Objects.requireNonNull("2020")))
//                .boxed()
//                .collect(Collectors.toList());
//
//        ArrayList<String> semesters = new ArrayList<>();
//        semesters.add("Spring");
//        semesters.add("Fall");
//        semesters.add("Summer");
//        semesters.add("Winter");
//
//        List<String> chars = new ArrayList<>();
//
//        for (char ch : "ABCDEFGHL".toCharArray()) {
//            chars.add(ch + "");
//        }
//
//        course_id.setItems(ids);
//
//        building.setItems(buildings);
//
//        year.setItems(FXCollections.observableList(years));
//
//        semester.setItems(FXCollections.observableArrayList(semesters));
//
//        time_slot.setItems(FXCollections.observableArrayList(chars));
//
//    }

    public void comboboxChange() {
        if (!course_id.equals("Select course")) {
            course_name.setText(db.getcourseName(course_id.getValue().toString()));
        }
    }

    public void loadRooms() {
        ObservableList<String> rooms
                = FXCollections.observableList(db.getABuildingRooms(building.getValue().toString()));
        room.setItems(rooms);
    }

    public void addSection() {
        String new_course_id = course_id.getValue().toString();
        String new_building = building.getValue().toString();
        String new_room = room.getValue().toString();
        String new_semester = semester.getValue().toString();
        String new_time_slot = time_slot.getValue().toString();
        String new_year = year.getValue().toString();

        if (!new_course_id.equals("Select course") && !new_building.equals("Select building") && !new_room.equals("Select room number")
                && !new_semester.equals("Select semester") && !new_time_slot.equals("Select time_slot") && !new_year.equals("Select year")) {

            if (!db.isClassCross(new_building, new_room, new_semester, Integer.parseInt(new_year), new_time_slot)) {

                conflict_in_room.setVisible(false);

                String msg = db.insertSection(new_course_id, new_building, new_room, new_semester, Integer.parseInt(new_year), new_time_slot);
                if (!msg.equals("")) {
                    notification.setText(msg);
                    notification.setVisible(true);
                    btn_hide.setVisible(true);

                    course_name.setText("");
                    setCombInitials();
                }
            } else {
                conflict_in_room.setVisible(true);
                System.out.println("conflict");
            }
        } else {
            System.err.println("error: nulls");
        }
    }

    public void hide() {
        notification.setVisible(false);
        btn_hide.setVisible(false);
    }

    public void back_to_start() {
        nav.navigateTo(rootPane, nav.MAIN_FXML);
    }

}
