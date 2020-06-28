package viewAndController;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML // fx:id="reportTypes"
    private ComboBox<String> reportTypes; // Value injected by FXMLLoader

    @FXML // fx:id="generateRptBTN"
    private Button generateRptBTN; // Value injected by FXMLLoader

    @FXML // fx:id="scheduleOfConsultantText"
    private TextArea reportText; // Value injected by FXMLLoader

    @FXML // fx:id="numAppointmentsBTN"
    private Button numAppointmentsBTN; // Value injected by FXMLLoader

    @FXML // fx:id="consultantScheduleBTN"
    private Button consultantScheduleBTN; // Value injected by FXMLLoader

    @FXML // fx:id="yearTotalBTN"
    private Button yearTotalBTN; // Value injected by FXMLLoader

    @FXML // fx:id="backBTN"
    private Button backBTN; // Value injected by FXMLLoader

    Appointment selectedReport = null;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportTypes.getItems().addAll("Total Customers", "Total Appointments By Month", "Schedule By Consultant");

    }

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
        switch (reportTypes.getSelectionModel().getSelectedItem()) {
            case "Total Customers": getTotalCustomers(); break;
            case "Total Appointments By Month": getAppointmentsByMonth(); break;
            case "Schedule By Consultant": consultantSchedule(); break;
        }

    }

    public void getAppointmentsByMonth() {
    }

    public void consultantSchedule() {
        // Look at city to pull from combo box
        // Grab month by index


    }

    public void appointmentsByYear(javafx.event.ActionEvent actionEvent) {
    }

    public void backHandler(javafx.event.ActionEvent actionEvent) {
    }

    public void getTotalCustomers() {
        String totalCustomers = "SELECT COUNT(customerId) FROM customer";
        try {
            Statement s = DbConnection.conn.createStatement();
            ResultSet rs = s.executeQuery(totalCustomers);
            if (rs.next()) {
                int count = rs.getInt(1);
                reportText.setText(Integer.valueOf(count).toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
