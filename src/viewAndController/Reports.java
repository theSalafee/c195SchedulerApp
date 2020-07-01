package viewAndController;

import database.AppointmentDB;
import database.DbConnection;
import database.UserDB;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static database.DbConnection.conn;

public class Reports implements Initializable {

    @FXML // fx:id="reportTypes"
    private ComboBox<String> reportTypes; // Value injected by FXMLLoader

    @FXML // fx:id="reportTypes"
    private ComboBox<String> reportTypes1; // Value injected by FXMLLoader

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

    public void generateReport(javafx.event.ActionEvent actionEvent) throws SQLException {
        switch (reportTypes.getSelectionModel().getSelectedItem()) {
            case "Total Customers":
                getTotalCustomers();
                break;

            case "Total Appointments By Month":
                int month = reportTypes1.getSelectionModel().getSelectedIndex() + 1;
                getAppointmentsByMonth(month);
                break;

            case "Schedule By Consultant":
                consultantSchedule(reportTypes1.getValue());
                break;
        }
    }

    @FXML
    void handleReport(ActionEvent event) {
        reportTypes1.setVisible(true);
        switch (reportTypes.getSelectionModel().getSelectedItem()) {
            case "Total Customers":
                reportTypes1.setVisible(false);
                break;
            case "Total Appointments By Month":
                reportTypes1.getItems().clear();
                reportTypes1.getItems().addAll("January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December");
                break;
            case "Schedule By Consultant":
                reportTypes1.getItems().clear();
                reportTypes1.getItems().addAll("Umm Ziyad", "test");
                /////reportTypes1.getItems().addAll(UserDB.getActiveUsers());
                break;
        }
    }

    public void getAppointmentsByMonth(int month) throws SQLException {
        reportText.clear();
        String sql = "SELECT * FROM appointment WHERE MONTH(start) = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, month);
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        int total = 0;
        while (rs.next()) {
            total = total + 1;
        }
        reportText.setText("The number of appointments for that month are: " + Integer.valueOf(total).toString());
    }

    public void consultantSchedule(String contact) throws SQLException {
        reportText.clear();
        String sql = "Select * from appointment WHERE contact = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, contact);
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        int total = 0;
        //int appointmentId  = rs.getInt("appointmentId");
        String name = "default";

        while (rs.next()) {

            total = total + 1;
            name = rs.getString("contact");
            name += " ";
            name += rs.getDate("start");
            name += " ";
            name += rs.getTime("start").toLocalTime();
            reportText.setText("The consultant schedule is: " + name + "\r\n");

            System.out.println(name);

        }

    }

    public void getTotalCustomers() {
        String totalCustomers = "SELECT COUNT(customerId) FROM customer";
        try {
            Statement s = DbConnection.conn.createStatement();
            ResultSet rs = s.executeQuery(totalCustomers);
            if (rs.next()) {
                int count = rs.getInt(1);
                reportText.setText("The number of total customers is : " + Integer.valueOf(count).toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
