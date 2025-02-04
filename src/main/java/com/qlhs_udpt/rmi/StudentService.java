package com.qlhs_udpt.rmi;
import com.qlhs_udpt.model.Student;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface StudentService extends Remote {
    void addStudent(Student student) throws RemoteException;
    void updateStudent(Student student) throws RemoteException;
    void deleteStudent(int studentId) throws RemoteException;
    List<Student> getAllStudents() throws RemoteException;
}
