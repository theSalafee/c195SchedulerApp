package viewAndController;

import com.sun.istack.internal.NotNull;
import database.AppointmentDB;
import database.CustomerDB;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    public Button backBtn;
    public Button addAppointmentBtn;
    public Button modifyCustomerBtn;
    public Button deleteAppointmentBtn;
    public AnchorPane appointmentsView;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<models.Appointment, String> customerName;


    @FXML
    private TableColumn<models.Appointment, ZonedDateTime> startTime;

    @FXML
    private TableColumn<models.Appointment, ZonedDateTime> endTime;

    @FXML
    private TableColumn<models.Appointment, String> type;

    static boolean isNewAppointment;
    static Customer selectedCustomer;
    ObservableList<Appointment> apptList = FXCollections.observableArrayList();


    public void setAppointmentsList() {
       appointmentsTable.setItems(AppointmentDB.getAllAppointments());

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        customerName.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomer().getCustomerName());
        });

        startTime.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper<ZonedDateTime>(cellData.getValue().getStart()) {
            };
        });

        endTime.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getEnd());
        });

        type.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getType());
        });
        

        apptList = AppointmentDB.getAllAppointments();
        appointmentsTable.getItems().addAll(apptList);
    }

    public void backBtnHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewAndController/mainMenu.fxml"));
        loader.load();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addHandler(ActionEvent actionEvent) {
    }

    public void modifyHandler(ActionEvent actionEvent) {
    }

    public void deleteHandler(ActionEvent actionEvent) {
    }

    public void handleWeekly(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Appointment> filteredData = new FilteredList<>(apptList);
        filteredData.setPredicate(row -> {

            LocalDateTime rowDate = row.getStart().toLocalDateTime();

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus7);
        });
        appointmentsTable.setItems(filteredData);
    }

    public void handleMonthly(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus30 = now.plusDays(30);
        FilteredList<Appointment> filteredData = new FilteredList<>(apptList);
        filteredData.setPredicate(row -> {

            LocalDateTime rowDate = row.getStart().toLocalDateTime();

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus30);
        });
        appointmentsTable.setItems(filteredData);
    }

    public void handleAll(ActionEvent actionEvent) {
        //appointmentsTable.getItems().clear();
        appointmentsTable.getItems().setAll(apptList);
       // appointmentsTable.getItems().addAll(apptList);
    }
}
