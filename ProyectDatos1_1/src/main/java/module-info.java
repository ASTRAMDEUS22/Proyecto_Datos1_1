module example.proyectdatos1_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires firmata4j;

    opens sample to javafx.fxml;
    exports sample;
}