package viewAndController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Appointments {
    public Button backBtn;
    public Button addAppointmentBtn;
    public Button modifyCustomerBtn;
    public Button deleteAppointmentBtn;
    public AnchorPane appointmentsView;
    public TableView appointmentsTable;
    public TableColumn customerName;

    static boolean isNewAppointment;
    static Customer selectedCustomer;


    public void initialize(URL url, ResourceBundle rb) {

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
}
