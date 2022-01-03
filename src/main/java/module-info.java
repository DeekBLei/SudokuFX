module SudokuFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires lightcouch;
    requires gson;
    requires org.slf4j;

    opens view to javafx.graphics, javafx.fxml;
    opens model to gson;
    opens controller to gson, javafx.fxml;
    opens Service.Solver to gson, javafx.fxml;
    opens Utility to gson, javafx.fxml;
}