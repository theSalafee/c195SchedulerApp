package viewAndController;
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
import sun.java2d.pipe.AAShapePipe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {

//    public ComboBox AppointmentDescField;
//    public DatePicker AppointmentDateField;
//    public ComboBox AppointmentStartField;
//    public ComboBox AppointmentEndField;
//    public Button saveBtn;
//    public Button cancelBtn;
//    public ComboBox contactField;
//    public TextField locationField;
//    public TextField customerName;

    @FXML
    private ComboBox<Appointment> contactField;

    @FXML
    private ComboBox<Appointment> AppointmentDescField;

    @FXML
    private TextField locationField;

    @FXML
    private DatePicker AppointmentDateField;

    @FXML
    private ComboBox<Appointment> AppointmentStartField;

    @FXML
    private ComboBox<Appointment> AppointmentEndField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField customerName;

    Stage stage;
    Parent scene;
    static boolean isNewAppointment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isNewAppointment = AppointmentController.isNewAppointment;
        if(!isNewAppointment){
            Appointment selectedAppointment = AppointmentController.getSelectedAppointment();
            customerName.setText(selectedAppointment.getCustomer().getCustomerName());


        }
    }

    public void saveHandler(ActionEvent actionEvent) {
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
