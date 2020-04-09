package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Appointments {
    public Button backBtn;
    public Button addAppointmentBtn;
    public Button modifyCustomerBtn;
    public Button deleteAppointmentBtn;
    public TableView customerTable;
    public TableColumn custName;
    public TableColumn custAddress1;
    public TableColumn custAddress2;
    public TableColumn custCity;
    public TableColumn custCounty;
    public TableColumn custPhone;
    public TableColumn custPostalCode;
    public AnchorPane appointmentsView;
    public TableView appointmentsTable;

    public void backBtnHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/mainMenu.fxml"));
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
}
