module com.example.waterplantcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.waterplantcalculator to javafx.fxml;
    exports com.example.waterplantcalculator;
}