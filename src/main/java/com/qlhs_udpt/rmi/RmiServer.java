package com.qlhs_udpt.rmi;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
public class RmiServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            StudentService studentService = new StudentServiceImpl();
            Naming.rebind("//localhost/StudentService", studentService);
            System.out.println("StudentService has been registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
