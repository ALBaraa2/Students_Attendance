package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.SharedData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyLectuer implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label CIL;

    @FXML
    private Label DL;

    @FXML
    private Button Done;

    @FXML
    private Label LL;

    @FXML
    private Label SIL;

    @FXML
    private Label SL;

    @FXML
    private Label TIL;

    @FXML
    private Label TL;

    @FXML
    private Label YL;

    @FXML
    private CheckBox date;

    @FXML
    private DatePicker dateT;

    @FXML
    private CheckBox location;

    @FXML
    private TextField locationT;

    @FXML
    private CheckBox time;

    @FXML
    private Spinner<Integer> houre;

    @FXML
    private Spinner<Integer> minutes;

    @FXML
    private CheckBox title;

    @FXML
    private TextField titleT;

    @FXML
    private Label massege;

    @FXML
    private ComboBox<String> lecture_id;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String course_id = SharedData.getInstance().getCourse_id();
    String year = SharedData.getInstance().getYear();
    String semester = SharedData.getInstance().getSemester();
    String sec_id = SharedData.getInstance().getSec_id();

    public void initialize(URL url, ResourceBundle rb) {
        CIL.setText(course_id);
        YL.setText(String.valueOf(year));
        SL.setText(semester);
        SIL.setText(String.valueOf(sec_id));
        ObservableList<String> ids = FXCollections.observableList(db.getLectureId(course_id, year, semester, sec_id));
        lecture_id.setItems(ids);
        minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        houre.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
        minutes.setEditable(true);
        houre.setEditable(true);
    }

    @FXML
    void backToCourse(ActionEvent event) {
        nav.navigateTo(root, nav.LECTURES_FXML);
    }

    @FXML
    void checkDate(ActionEvent event) {
        dateT.setVisible(true);
        DL.setVisible(true);
        Done.setVisible(true);
        if (!date.isSelected()) {
            dateT.setVisible(false);
            DL.setVisible(false);
        }
        if (!date.isSelected() && !time.isSelected() && !title.isSelected() && !location.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void checkLocation(ActionEvent event) {
        locationT.setVisible(true);
        LL.setVisible(true);
        Done.setVisible(true);
        if (!location.isSelected()) {
            locationT.setVisible(false);
            LL.setVisible(false);
        }
        if (!date.isSelected() && !time.isSelected() && !title.isSelected() && !location.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void checkTime(ActionEvent event) {
        minutes.setVisible(true);
        houre.setVisible(true);
        TIL.setVisible(true);
        Done.setVisible(true);
        if (!time.isSelected()) {
            minutes.setVisible(false);
            houre.setVisible(false);
            TIL.setVisible(false);
        }
        if (!date.isSelected() && !time.isSelected() && !title.isSelected() && !location.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void checkTitle(ActionEvent event) {
        titleT.setVisible(true);
        TL.setVisible(true);
        Done.setVisible(true);
        if (!title.isSelected()) {
            titleT.setVisible(false);
            TL.setVisible(false);
        }
        if (!date.isSelected() && !time.isSelected() && !title.isSelected() && !location.isSelected()) {
            Done.setVisible(false);
        }
    }

    @FXML
    void doneModify(ActionEvent event) {
        if (lecture_id.getValue() != null) {
            if (date.isSelected() && time.isSelected() && title.isSelected() && location.isSelected()) {
                if (dateT.getValue() != null && titleT.getText() != null && houre.getValue().equals(0) && locationT.getText() != null) {
                    String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                    LocalDate localDate = dateT.getValue();
                    String date = String.valueOf(localDate);
                    if (db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText()) &&
                            db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                            db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date) &&
                            db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText())) {
                        massege.setText("Modified successfully");
                        massege.setTextFill(Color.GREEN);
                        massege.setVisible(true);
                    } else {
                        massege.setText("Modification failed");
                        massege.setTextFill(Color.RED);
                        massege.setVisible(true);
                    }
                } else {
                    massege.setText("Complete data registration");
                    massege.setTextFill(Color.RED);
                    massege.setVisible(true);
                }
            } else {
                if (date.isSelected() && !time.isSelected() && !title.isSelected() && !location.isSelected()) {
                    if (dateT.getValue() != null) {
                        LocalDate localDate = dateT.getValue();
                        String date = String.valueOf(localDate);
                        if (db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date)) {
                            massege.setText("Modified successfully");
                            massege.setTextFill(Color.GREEN);
                            massege.setVisible(true);
                        } else {
                            massege.setText("Modification failed");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else {
                        massege.setText("Complete data registration");
                        massege.setTextFill(Color.RED);
                        massege.setVisible(true);
                    }
                } else {
                    if (date.isSelected() && time.isSelected() && !title.isSelected() && !location.isSelected()) {
                        if (dateT.getValue() != null && houre.getValue() != 0) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date)) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (date.isSelected() && !time.isSelected() && title.isSelected() && !location.isSelected()) {
                        if (dateT.getValue() != null && titleT.getText() != null) {
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date) &&
                                    db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (date.isSelected() && !time.isSelected() && !title.isSelected() && location.isSelected()) {
                        if (dateT.getValue() != null && locationT.getText() != null) {
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date) &&
                                    db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (date.isSelected() && time.isSelected() && title.isSelected() && !location.isSelected()) {
                        if (dateT.getValue() != null && titleT.getText() != null && houre.getValue() != 0) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText()) &&
                                    db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date)) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (date.isSelected() && time.isSelected() && !title.isSelected() && location.isSelected()) {
                        if (dateT.getValue() != null && locationT.getText() != null && houre.getValue() != 0) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText()) &&
                                    db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date)) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (date.isSelected() && !time.isSelected() && title.isSelected() && location.isSelected()) {
                        if (dateT.getValue() != null && titleT.getText() != null && houre.getValue() != 0) {
                            LocalDate localDate = dateT.getValue();
                            String date = String.valueOf(localDate);
                            if (db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText()) &&
                                    db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText()) &&
                                    db.modifyLectureDate(lecture_id.getValue(), course_id, year, semester, sec_id, date)) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && time.isSelected() && !title.isSelected() && !location.isSelected()) {
                        if (houre.getValue() != null) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            if (db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time)) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && time.isSelected() && title.isSelected() && !location.isSelected()) {
                        if (houre.getValue() != null && titleT.getText() != null) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            if (db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && time.isSelected() && !title.isSelected() && location.isSelected()) {
                        if (houre.getValue() != null && locationT.getText() != null) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            if (db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && time.isSelected() && title.isSelected() && location.isSelected()) {
                        if (houre.getValue() != null && locationT.getText() != null && houre.getValue() != null) {
                            String time = houre.getValue().toString() + ":" + minutes.getValue().toString() + ":00";
                            if (db.modifyLectureTime(lecture_id.getValue(), course_id, year, semester, sec_id, time) &&
                                    db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText()) &&
                                    db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && !time.isSelected() && title.isSelected() && !location.isSelected()) {
                        if (titleT.getText() != null) {
                            if (db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && !time.isSelected() && title.isSelected() && !location.isSelected()) {
                        if (titleT.getText() != null && locationT.getText() != null) {
                            if (db.modifyLectureTitle(lecture_id.getValue(), course_id, year, semester, sec_id, titleT.getText()) &&
                                    db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    } else if (!date.isSelected() && !time.isSelected() && !title.isSelected() && location.isSelected()) {
                        if (locationT.getText() != null) {
                            if (db.modifyLectureLocation(lecture_id.getValue(), course_id, year, semester, sec_id, locationT.getText())) {
                                massege.setText("Modified successfully");
                                massege.setTextFill(Color.GREEN);
                                massege.setVisible(true);
                            } else {
                                massege.setText("Modification failed");
                                massege.setTextFill(Color.RED);
                                massege.setVisible(true);
                            }
                        } else {
                            massege.setText("Complete data registration");
                            massege.setTextFill(Color.RED);
                            massege.setVisible(true);
                        }
                    }
                }
            }
        } else {
            massege.setText("Select Sec_id");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }
}