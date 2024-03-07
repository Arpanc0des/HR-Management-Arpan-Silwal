module com.example.hr_management_arpan_silwal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires mysql.connector.j;
    requires java.sql;

    opens com.example.hr_management_arpan_silwal to javafx.fxml;
    exports com.example.hr_management_arpan_silwal;
    opens com.example.hr_management_arpan_silwal.Model to javafx.fxml;
    exports com.example.hr_management_arpan_silwal.Model;
}