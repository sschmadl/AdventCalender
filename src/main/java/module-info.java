module htl.steyr.adventcalender {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.steyr.adventcalender to javafx.fxml;
    exports htl.steyr.adventcalender;
}