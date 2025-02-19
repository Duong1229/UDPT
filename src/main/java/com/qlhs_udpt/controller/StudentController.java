package com.qlhs_udpt.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField majorField;
    @FXML
    private TextField hometownField;
    @FXML
    private TextField academicYearField;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, String> hometownColumn;
    @FXML
    private TableColumn<Student, String> majorColumn;
    @FXML
    private TableColumn<Student, String> academicYearColumn;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        hometownColumn.setCellValueFactory(new PropertyValueFactory<>("hometown"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        academicYearColumn.setCellValueFactory(new PropertyValueFactory<>("academicYear"));

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
            ObservableList<Student> studentList = FXCollections.observableArrayList(students);
            studentTableView.setItems(studentList);
        } catch (RemoteException e) {
            showError("Error", "Tải danh sách từ server thất bại.");
            throw e;
        }
    }
    @FXML
    public void addStudent(MouseEvent event) {
        String name = nameField.getText();
        String gender = genderField.getText();
        String hometown = hometownField.getText();
        String major = majorField.getText();
        String academicYear = academicYearField.getText();
        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            showError("Input Error", "Nhập đúng giá trị của tuổi.");
            return;
        }
        Student student = new Student(0, name, gender, age, hometown, major, academicYear );
        try {
            studentService.addStudent(student);
            loadStudentList();
            clearFields();
            showSuccess("Success", "Thêm học sinh thành công!");
        } catch (RemoteException e) {
            showError("Error", "Thêm học sinh thất bại.");
            e.printStackTrace();
        }
    }
    @FXML
    public void updateStudent(MouseEvent event) {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            selectedStudent.setName(nameField.getText());
            selectedStudent.setGender(genderField.getText());
            selectedStudent.setHometown(hometownField.getText());
            selectedStudent.setMajor(majorField.getText());
            selectedStudent.setAcademicYear(academicYearField.getText());
            try {
                selectedStudent.setAge(Integer.parseInt(ageField.getText()));
            } catch (NumberFormatException e) {
                showError("Input Error", "Nhập đúng giá trị của tuổi.");
                return;
            }
            try {
                studentService.updateStudent(selectedStudent);
                loadStudentList();
                clearFields();
                showSuccess("Success", "Cập nhật học sinh thành công!");
            } catch (RemoteException e) {
                showError("Error", "Cập nhật học sinh thất bại.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteStudent(MouseEvent event) {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                studentService.deleteStudent(selectedStudent.getId());
                loadStudentList();
                showSuccess("Success", "Xóa sinh viên thành công");
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
            writer.write("DANH SÁCH SINH VIÊN \n");
            writer.write("ID\tHọ và tên\tGiới tính\tTuổi\tQuê quán\tNgành học\tKhóa học \n");
            for (Student student : students) {
                writer.write(student.getId() + "\t" + student.getName() + "\t" + student.getGender() + "\t" + student.getAge() + "\t" + student.getHometown() + "\t" + student.getMajor() + "\t" + student.getAcademicYear() + "\n");

            }
            writer.close();
            showSuccess("Export Successful", "Xuất danh sách thành công.");
        } catch (RemoteException e) {
            showError("Error", "Xuất danh sách thất bại.");
            e.printStackTrace();
        } catch (IOException e) {
            showError("File Error", "Không thể xuất file.");
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
    private void clearFields() {
        nameField.clear();
        genderField.clear();
        ageField.clear();
        majorField.clear();
        hometownField.clear();
        academicYearField.clear();
    }

}
