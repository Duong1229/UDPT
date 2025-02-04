module com.example.qlhs_udpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.rmi;
    requires java.sql;
    exports com.qlhs_udpt.client;
    opens com.qlhs_udpt.client to javafx.fxml;
    exports com.qlhs_udpt.controller;
    opens com.qlhs_udpt.controller to javafx.fxml;
    exports com.qlhs_udpt.rmi;
}
