package viewAndController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class Reports {

    @FXML // fx:id="reportTypes"
    private ComboBox<?> reportTypes; // Value injected by FXMLLoader

    @FXML // fx:id="generateRptBTN"
    private Button generateRptBTN; // Value injected by FXMLLoader

    @FXML // fx:id="scheduleOfConsultantText"
    private TextArea scheduleOfConsultantText; // Value injected by FXMLLoader

    @FXML // fx:id="totalAppointmentsThisYearText"
    private TextArea totalAppointmentsThisYearText; // Value injected by FXMLLoader

    @FXML // fx:id="numAppointmentsBTN"
    private Button numAppointmentsBTN; // Value injected by FXMLLoader

    @FXML // fx:id="consultantScheduleBTN"
    private Button consultantScheduleBTN; // Value injected by FXMLLoader

    @FXML // fx:id="yearTotalBTN"
    private Button yearTotalBTN; // Value injected by FXMLLoader

    @FXML // fx:id="backBTN"
    private Button backBTN; // Value injected by FXMLLoader

    @FXML
    public void backBtnHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewAndController/mainMenu.fxml"));
        loader.load();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void generateReport(javafx.event.ActionEvent actionEvent) {
    }

    public void getAppointmentsByMonth(javafx.event.ActionEvent actionEvent) {
    }

    public void consultantSchedule(javafx.event.ActionEvent actionEvent) {
    }

    public void appointmentsByYear(javafx.event.ActionEvent actionEvent) {
    }

    public void backHandler(javafx.event.ActionEvent actionEvent) {
    }
}
