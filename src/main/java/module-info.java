module com.example.geometrix {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.geometrix to javafx.fxml;
    exports com.example.geometrix;
}