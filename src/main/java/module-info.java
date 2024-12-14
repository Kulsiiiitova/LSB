module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}