package com.example.test_javafx.controllers;

import com.example.test_javafx.Navigation;
import com.example.test_javafx.models.AttendanceSheet;
import com.example.test_javafx.models.CmboBoxAutoComplete;
import com.example.test_javafx.models.DBModel;
import com.example.test_javafx.models.SharedData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
<<<<<<< HEAD
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
=======
>>>>>>> e0bdc71cf4b8e91fb6d5da2d242a9cdc5b9f0097
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.Desktop;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SheetOfNonCompliant implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<AttendanceSheet, String> Student_Name;

    @FXML
    private TableColumn<AttendanceSheet, Double> attendancePercentage;

    @FXML
    private TableView<AttendanceSheet> sheet;

    @FXML
    private ComboBox<String> courseIdCom;

    @FXML
    private Label error;

    @FXML
    private Button xlx;

    DBModel db = new DBModel();
    Navigation nav = new Navigation();
    String email = SharedData.getInstance().getEmail();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Student_Name.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        attendancePercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage")); // تأكد من تطابق اسم الخاصية في النموذج
        setComboBoxes();
    }

    private void setComboBoxes() {
        ObservableList<String> ids = FXCollections.observableList(db.getCourseIDsOfAssistantByEmail(email));
        CmboBoxAutoComplete.cmboBoxAutoComplete(courseIdCom , ids);
    }

    @FXML
    void back(ActionEvent event) {
        nav.navigateTo(root , nav.REPORT_FXML);
    }

    @FXML
    void view(ActionEvent event) {
        sheet.setItems(FXCollections.observableArrayList(db.SheetOfNonCompliant(courseIdCom.getValue())));
    }

    @FXML
    void XLSX(ActionEvent event) throws IOException {
<<<<<<< HEAD
        String newSheetName = courseIdCom.getValue();
        xlx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage fileChooserStage = new Stage();
                fileChooserStage.setTitle("File Chooser");
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedFolder = directoryChooser.showDialog(fileChooserStage);

                if (selectedFolder != null) {
                    // اسم الملف الذي ترغب في إنشائه
                    String fileName = "SheetOfNonCompliant.xlsx";

                    // تعيين المسار الكامل للملف الذي ترغب في حفظه
                    String filePath = selectedFolder.getPath() + "/" + fileName;

                    // إنشاء مصنف Excel جديد
                    Workbook workbook = new XSSFWorkbook();

                    // Create a new sheet
=======
        String filePath = "C:\\Users\\albaraa\\Downloads\\s_1.xlsx";
        String newSheetName = courseIdCom.getValue();
        File file = new File(filePath);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                // Create a new sheet
>>>>>>> e0bdc71cf4b8e91fb6d5da2d242a9cdc5b9f0097
                if (workbook.getSheet(newSheetName) == null) {
                    Sheet sheet = workbook.createSheet(newSheetName);
                    // Write data to the new sheet
                    int size = db.SheetOfNonCompliant(newSheetName).size();
                    ArrayList<AttendanceSheet> x = db.SheetOfNonCompliant(courseIdCom.getValue());
                    for (int i = 0; i < size; i++) {
                        Row row = sheet.createRow(i);
                        Cell cell1 = row.createCell(0);
                        Cell cell2 = row.createCell(1);
                        cell1.setCellValue(x.get(i).getStudent_name());
                        cell2.setCellValue(x.get(i).getAttendancePercentage() + "%");
                    }
                    error.setText("");
                } else {
                    error.setText("this course is already exsist");
                }
<<<<<<< HEAD

                    try {
                        // حفظ المصنف Excel في الملف المحدد
                        FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                        workbook.write(outputStream);
                        workbook.close();
                        outputStream.close();
                        // فتح الملف إذا كان موجودًا
                        File file = new File(filePath);
                        if (file.exists()) {
                            Desktop.getDesktop().open(file);
                        }else{

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
=======
                try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\albaraa\\Downloads\\s_1.xlsx")) {
                    workbook.write(outputStream);
                    workbook.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
>>>>>>> e0bdc71cf4b8e91fb6d5da2d242a9cdc5b9f0097
                }
            }
        });
    }

}


