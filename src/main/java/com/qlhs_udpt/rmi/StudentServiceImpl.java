package com.qlhs_udpt.rmi;
import com.qlhs_udpt.database.DatabaseConnect;
import com.qlhs_udpt.model.Student;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class StudentServiceImpl extends UnicastRemoteObject implements StudentService {
    private Connection connection;
    public StudentServiceImpl() throws RemoteException {
        super();
        try {
            connection = DatabaseConnect.getConnection();
            if (connection == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi kết nối cơ sở dữ liệu", e);
        }
    }
    @Override
    public void addStudent(Student student) throws RemoteException {
        String query = "INSERT INTO students (name, gender, age, hometown, major, academicYear) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getHometown());
            stmt.setString(5, student.getMajor());
            stmt.setString(6, student.getAcademicYear());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi thêm sinh viên", e);
        }
    }
    @Override
    public void updateStudent(Student student) throws RemoteException {
        String query = "UPDATE students SET name = ?, gender = ?, age = ?, hometown = ?, major = ?, academicYear = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getHometown());
            stmt.setString(5, student.getMajor());
            stmt.setString(6, student.getAcademicYear());
            stmt.setInt(7, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi cập nhật sinh viên", e);
        }
    }
    @Override
    public void deleteStudent(int studentId) throws RemoteException {
        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi xóa sinh viên", e);
        }
    }
    @Override
    public List<Student> getAllStudents() throws RemoteException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                int age = rs.getInt("age");
                String hometown = rs.getString("hometown");
                String major = rs.getString("major");
                String academicYear = rs.getString("academicYear");
                students.add(new Student(id, name, gender, age, hometown, major, academicYear));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi lấy danh sách sinh viên", e);
        }
        return students;
    }
}