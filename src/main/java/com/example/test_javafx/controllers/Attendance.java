package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.AttendanceSheet;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import com.example.test_javafx.models.SharedData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.poi.ss.usermodel.Cell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Attendance implements Initializable {

    @FXML
    private ComboBox<String> LName;

    @FXML
    private ComboBox<String> courseID;

    @FXML
    private Label massege;

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> sec_id;

    @FXML
    private TableColumn<AttendanceSheet,String> status;

    @FXML
    private ComboBox<String> student;

    @FXML
    private TableColumn<AttendanceSheet, String> lecture_title;

    @FXML
    private TableView<AttendanceSheet> table;

    @FXML
    private Button xlx;

    @FXML
    private ComboBox<String> semester;

    @FXML
    private ComboBox<String> year;

    DBModel db = DBModel.getModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //وضع البيانات داخل الاعمدة في tableview
        status.setCellValueFactory(new PropertyValueFactory<>("attendance_status"));
        lecture_title.setCellValueFactory(new PropertyValueFactory<>("lecture_title"));
        //تفعيل ميثودsetComboBoxes عند تشغيل البرنامج
        setComboBoxes();
    }

    //وضع الcourse الخاص بالمعيد الذي يقوم بالدخول الى البرنامج في الComboBox الخاص بcourseID ومن ثم يتم تقعيلsetComboBoxesSec_id
    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDs(email));
        courseID.setItems(ids);
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseID, ids);
        courseID.setOnAction(this::setComboBoxesYear);
    }

    private void setComboBoxesYear(ActionEvent event1) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        if (course_id != null) {
            ObservableList<String> years = FXCollections.observableList(db.getYearsToteachAssistant(email, course_id));
            year.setItems(years);
            year.setOnAction(this::setComboBoxesSemester);
        }
    }

    private void setComboBoxesSemester(ActionEvent event1) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String Syear = year.getSelectionModel().getSelectedItem();
        if (course_id != null && Syear != null) {
            ObservableList<String> semesters = FXCollections.observableList(db.getSemestersToteachAssistant(email, course_id, Syear));
            semester.setItems(semesters);
            semester.setOnAction(this::setComboBoxesSec_id);
        }
    }

    // وضع الSec_id الخاص بالcourse (الذي يتم وضعه في الميثود السابقة) في الComboBox الخاص بsec_id
    private void setComboBoxesSec_id(ActionEvent event1) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String Syear = year.getSelectionModel().getSelectedItem();
        String Ssemseter = semester.getSelectionModel().getSelectedItem();
        if (course_id != null && Syear != null && Ssemseter != null) {
            ObservableList<String> sec_ids = FXCollections.observableList(db.getSecIds(course_id,
                    Integer.parseInt(Syear), Ssemseter));
            sec_id.setItems(sec_ids);
            EventHandler<ActionEvent> comboEvent = (ActionEvent event) -> {
                setComboBoxesLecturename(event);
                setComboBoxesStudent(event);
            };
            sec_id.setOnAction(comboEvent);
        }
    }

    // وضع الLecturename الخاص بالcourse (الذي يتم وضعه في ميثود setComboBoxes) في الComboBox الخاص بLName
    private void setComboBoxesLecturename(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String Syear = year.getSelectionModel().getSelectedItem();
        String Ssemseter = semester.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        if (course_id != null && Syear != null && Ssemseter != null && secid != null) {
            ObservableList<String> lectureNames = FXCollections.observableList(db.getLecturesTitle(course_id,
                    Integer.parseInt(Syear), Ssemseter, Integer.parseInt(secid)));
            LName.setItems(lectureNames);
            CmboBoxAutoComplete.cmboBoxAutoComplete(LName, lectureNames);
        }
    }

    private void setComboBoxesStudent(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String Syear = year.getSelectionModel().getSelectedItem();
        String Ssemseter = semester.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        if (course_id != null && Syear != null && Ssemseter != null) {
            ObservableList<String> students = FXCollections.observableList(db.getStudents(course_id,
                    Syear, Ssemseter, secid));
            student.setItems(students);
            CmboBoxAutoComplete.cmboBoxAutoComplete(student, students);
        }
    }

    @FXML
    void AttendanceReport(ActionEvent event) {
        String course_id = courseID.getSelectionModel().getSelectedItem();
        String secid = sec_id.getSelectionModel().getSelectedItem();
        String Student = student.getSelectionModel().getSelectedItem();
        if (student.getValue() != null && LName.getValue() != null && courseID.getValue() != null && sec_id.getValue() != null
                && year.getValue() != null && semester.getValue() != null) {
            table.setItems(FXCollections.observableArrayList(db.getAttendanceReport
                    (course_id, Integer.parseInt(year.getValue()), semester.getValue(), Integer.parseInt(secid), Student)));
        } else {
            massege.setText("Complete the data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    @FXML
    void Checkin(ActionEvent event) {
        if (student.getValue() != null && LName.getValue() != null && courseID.getValue() != null && sec_id.getValue() != null
                && year.getValue() != null && semester.getValue() != null){
            if (db.attendance(student.getValue(),courseID.getValue(),year.getValue(),semester.getValue(),sec_id.getValue(),LName.getValue())){
                massege.setText("Done");
                massege.setTextFill(Color.GREEN);
                massege.setVisible(true);
            } else {
                massege.setText("Failed");
                massege.setTextFill(Color.RED);
                massege.setVisible(true);
            }
        } else {
            massege.setText("Complete the data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    @FXML
    void modify(ActionEvent event) {
        if (student.getValue() != null && LName.getValue() != null && courseID.getValue() != null && sec_id.getValue() != null
                && year.getValue() != null && semester.getValue() != null) {
            if (db.modidyAttendance(student.getValue(), courseID.getValue(), year.getValue(), semester.getValue(), sec_id.getValue(), LName.getValue())) {
                massege.setText("Done");
                massege.setTextFill(Color.GREEN);
                massege.setVisible(true);
            } else {
                massege.setText("Failed");
                massege.setTextFill(Color.RED);
                massege.setVisible(true);
            }
        } else {
            massege.setText("Complete the data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root, nav.TEACH_ASSISTANT_FXML);
    }

    public void xlsx() {
        if (LName.getValue() != null && courseID.getValue() != null && sec_id.getValue() != null
                && year.getValue() != null && semester.getValue() != null) {
            xlx.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose File");
                // تحديد نوع الملفات المسموح بها
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.xlsx")
                );
                Stage fileChooserStage = new Stage();
                fileChooserStage.setTitle("File Chooser");
                // الحصول على الملف المختار عند النقر على زر "Open"
                java.io.File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
                if (selectedFile != null) {
                    readExcelFile(selectedFile.getAbsolutePath());
                } else {
                    System.out.println("No file selected.");
                }
            });
        } else {
            massege.setText("Complete the data");
            massege.setTextFill(Color.RED);
            massege.setVisible(true);
        }
    }

    //قراءة id الطالب من ملف اكسيل ووضع الطالب في حالة حضور
    public void readExcelFile(String filePath) {
        try {
            // تحميل ملف Excel
            Workbook workbook = WorkbookFactory.create(new File(filePath));
            // افتح صفحة العمل الأولى
            Sheet sheet = workbook.getSheetAt(0);
            // قم بتصفح الصفوف والأعمدة واطبع قيمة كل خلية
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.NUMERIC) {
                        String value = String.valueOf(cell.getNumericCellValue());
                        String formattedValue = value.replace(".", "").substring(0, value.length() - 3);
                        db.attendance(formattedValue,courseID.getValue(),year.getValue(),semester.getValue()
                                ,sec_id.getValue(),LName.getValue());
                    }
                }
            }
            // أغلق ملف Excel
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}