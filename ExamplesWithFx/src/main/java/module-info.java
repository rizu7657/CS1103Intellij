module za.co.ruhanstudies.exampleswithfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens za.co.ruhanstudies.exampleswithfx to javafx.fxml;
    exports za.co.ruhanstudies.exampleswithfx;
}