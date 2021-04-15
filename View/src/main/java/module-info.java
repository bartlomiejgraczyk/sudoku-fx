module View {
    requires Model;
    requires java.sql;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens view;
    exports view;
    exports view.exceptions;
}