package com.qlhs_udpt.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.qlhs_udpt.rmi.StudentService;
import com.qlhs_udpt.model.Student;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class StudentController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField ageField;
    @FXML
    private ListView<Student> studentListView;

    private StudentService studentService;

    public StudentController() {
        super();
        try {
            studentService = (StudentService) Naming.lookup("rmi://localhost/StudentService");
        } catch (Exception e) {
            showError("Error", "Kết nối server thất bại.");
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        try {
            loadStudentList();
        } catch (RemoteException e) {
            showError("Error", "Tải danh sách thất bại.");
            e.printStackTrace();
        }
    }
    private void loadStudentList() throws RemoteException {
        try {
            List<Student> students = studentService.getAllStudents();
            studentListView.getItems().setAll(students);
        } catch (RemoteException e) {
            showError("Error", "Tải danh sách từ server thất bại.");
            throw e;
        }
    }
    @FXML
    public void addStudent(MouseEvent event) {
        String name = nameField.getText();
        String gender = genderField.getText();
        int age = 0;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            showError("Input Error", "NHập đúng giá trị của tuổi.");
            return;
        }

        Student student = new Student(0, name, gender, age);
        try {
            studentService.addStudent(student);
            loadStudentList();
        } catch (RemoteException e) {
            showError("Error", "Thêm học sinh thất bại.");
            e.printStackTrace();
        }
    }
    @FXML
    public void updateStudent(MouseEvent event) {
        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            selectedStudent.setName(nameField.getText());
            selectedStudent.setGender(genderField.getText());
            try {
                selectedStudent.setAge(Integer.parseInt(ageField.getText()));
            } catch (NumberFormatException e) {
                showError("Input Error", "NHập đúng giá trị của tuổi.");
                return;
            }
            try {
                studentService.updateStudent(selectedStudent);
                loadStudentList();
            } catch (RemoteException e) {
                showError("Error", "Cập nhật học sinh thất bại.");
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void deleteStudent(MouseEvent event) {
        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                studentService.deleteStudent(selectedStudent.getId());
                loadStudentList();
            } catch (RemoteException e) {
                showError("Error", "Không thể xóa học sinh.");
                e.printStackTrace();
            }
        } else {
            showError("Selection Error", "Không có học sinh để truy cập.");
        }
    }

    @FXML
    public void exportStudentListToFile(MouseEvent event) {
        try {
            List<Student> students = studentService.getAllStudents();
            BufferedWriter writer = new BufferedWriter(new FileWriter("student_list.txt"));
            writer.write("ID\tName\tGender\tAge\n");
            for (Student student : students) {
                writer.write(student.getId() + "\t" + student.getName() + "\t" + student.getGender() + "\t" + student.getAge() + "\n");
            }
            writer.close();
            showSuccess("Export Successful", "Xuất danh sách thành công.");
        } catch (RemoteException e) {
            showError("Error", "Xuất danh sách thất bại.");
            e.printStackTrace();
        } catch (IOException e) {
            showError("File Error", "Không thể xuâất file.");
            e.printStackTrace();
        }
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
