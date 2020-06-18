package viewAndController;
import database.CustomerDB;
import database.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {

    @FXML
    private ComboBox<User> contactField;

    @FXML
    private ComboBox<String> AppointmentDescField;

    @FXML
    private ComboBox<String> locationField;

    @FXML
    private DatePicker AppointmentDateField;

    @FXML
    private ComboBox<String> AppointmentStartField;

    @FXML
    private ComboBox<String> AppointmentEndField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Customer> customerName;

    Stage stage;
    Parent scene;
    static boolean isNewAppointment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isNewAppointment = AppointmentController.isNewAppointment;
        AppointmentStartField.getItems().addAll("8:00", "8:15", "8:30", "8:45", "9:00", "9:15",
                "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45", "2:00", "2:15",
                "2:30", "2:45", "3:00", "3:15", "3:30", "3:45", "4:00", "4:15", "4:30", "4:45");
        AppointmentEndField.getItems().addAll( "8:15", "8:30", "8:45", "9:00", "9:15",
                "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45", "2:00", "2:15", "2:30",
                "2:45", "3:00", "3:15", "3:30", "3:45", "4:00", "4:15", "4:30", "4:45", "5:00");
        AppointmentDescField.getItems().addAll("Intro Call", "Update Call", "Close Call");
        contactField.getItems().addAll(UserDB.getActiveUsers());
        customerName.getItems().addAll(CustomerDB.getActiveCustomers());
        locationField.getItems().addAll("Main Campus", "Valley Campus", "University District");

        if(!isNewAppointment){
            Appointment selectedAppointment = AppointmentController.getSelectedAppointment();
            customerName.setValue(selectedAppointment.getCustomer());
        }
    }

    public void saveHandler(ActionEvent actionEvent) {
        LocalDate date = AppointmentDateField.getValue();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        String start = AppointmentStartField.getValue();
        LocalTime ltStart = LocalTime.parse(start, dtf);
        ZonedDateTime zdtStart = ZonedDateTime.of(date, ltStart, ZoneId.systemDefault());
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp tsStart = Timestamp.valueOf(utcStart.toLocalDateTime());

        String end = AppointmentEndField.getValue();
        LocalTime ltEnd = LocalTime.parse(end, dtf);
        ZonedDateTime zdtEnd = ZonedDateTime.of(date, ltEnd, ZoneId.systemDefault());
        ZonedDateTime utcEnd = zdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp tsEnd = Timestamp.valueOf(utcEnd.toLocalDateTime());

    }

    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
