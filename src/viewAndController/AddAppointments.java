package viewAndController;
import Exceptions.AppointmentException;
import database.AppointmentDB;
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
import java.sql.PreparedStatement;
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
    private  ComboBox<String> cboType;

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
    private int appointmentId = 0;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isNewAppointment = AppointmentController.isNewAppointment;
        AppointmentStartField.getItems().addAll("08:00", "08:15", "08:30", "08:45", "09:00", "09:15",
                "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15",
                "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45");
        AppointmentEndField.getItems().addAll( "08:15", "08:30", "08:45", "09:00", "09:15",
                "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30",
                "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00");
        cboType.getItems().addAll("Intro Call", "Update Call", "Close Call");
        contactField.getItems().addAll(UserDB.getActiveUsers());
        customerName.getItems().addAll(CustomerDB.getActiveCustomers());
        locationField.getItems().addAll("Main Campus", "Valley Campus", "University District");

        if(!isNewAppointment){
            Appointment selectedAppointment = AppointmentController.getSelectedAppointment();
            appointmentId = selectedAppointment.getAppointmentId();
            customerName.setValue(selectedAppointment.getCustomer());
            AppointmentStartField.setValue(selectedAppointment.getStart().toLocalTime().toString());
            AppointmentEndField.setValue(selectedAppointment.getEnd().toLocalTime().toString());
            cboType.setValue(selectedAppointment.getType());
            locationField.setValue("Main Campus");
            AppointmentDateField.setValue(selectedAppointment.getStart().toLocalDate());
            contactField.setValue(UserDB.getUserById(selectedAppointment.getUserId()));
        }
    }

    public void saveHandler(ActionEvent actionEvent) throws AppointmentException {
        if(customerName.getSelectionModel().getSelectedItem() == null ||
                contactField.getSelectionModel().getSelectedItem() == null ||
                cboType.getSelectionModel().getSelectedItem() == null ||
                locationField.getSelectionModel().getSelectedItem() == null ||
                AppointmentStartField.getSelectionModel().getSelectedItem().isEmpty() ||
                AppointmentEndField.getSelectionModel().getSelectedItem().isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Appointment Exception");
            alert.setContentText("Null Field, please fill in all of the fields");
            alert.showAndWait();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            throw new AppointmentException("Null Exception Error");

        }else{
            int customerId = customerName.getSelectionModel().getSelectedItem().getCustomerId();
            int userId = contactField.getSelectionModel().getSelectedItem().getUserId();
            String type = cboType.getSelectionModel().getSelectedItem();
            LocalDate date = AppointmentDateField.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

            String start = AppointmentStartField.getValue();
            LocalTime ltStart = LocalTime.parse(start, dtf);
            ZonedDateTime zdtStart = ZonedDateTime.of(date, ltStart, ZoneId.systemDefault());
            ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp pStart = Timestamp.valueOf(utcStart.toLocalDateTime());

            String end = AppointmentEndField.getValue();
            LocalTime ltEnd = LocalTime.parse(end, dtf);
            ZonedDateTime zdtEnd = ZonedDateTime.of(date, ltEnd, ZoneId.systemDefault());
            ZonedDateTime utcEnd = zdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp pEnd = Timestamp.valueOf(utcEnd.toLocalDateTime());


            if(AppointmentDB.checkOverlap(pStart, pEnd, userId, appointmentId )){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("WGU Scheduling App");
                alert.setHeaderText("Add Appointment");
                alert.setContentText("Overlapping Appointments?");
                alert.showAndWait();
                return;
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Appointment");
            alert.setContentText("Are you sure you want to add/modify this Appointment?");
            alert.showAndWait();

            if(isNewAppointment){
                AppointmentDB.addAppointment(customerId, userId, type, utcStart, utcEnd);
            }else {
                AppointmentDB.updateAppointment(appointmentId, customerId, userId, type, utcStart, utcEnd);
            }

            try {
                scene = FXMLLoader.load(getClass().getResource("/viewAndController/appointments.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();

        }

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
