package viewAndController;

import database.CustomerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import models.Customer;

import java.io.IOException;

public class AddCustomers {
    public TextField addressTwo;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField customerName;
    public TextField addressOne;
    public TextField city;
    public TextField country;
    public TextField phone;
    public TextField postalCode;
    Stage stage;
    Parent scene;

    public void saveHandler(ActionEvent actionEvent) throws IOException {

        String customerNameText = customerName.getText();
        String addressOneText = addressOne.getText();
        String addressTwoText = addressTwo.getText();
        String cityText = city.getText();
        String countryText = country.getText();
        String phoneText = phone.getText();
        String postalCodeText = postalCode.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WGU Scheduling App");
        alert.setHeaderText("Add Customer");
        alert.setContentText("Are you sure you want to add this customer?");
        alert.showAndWait();

        //CustomerDB.addCustomer(new Customer(customerName, addressOne, addressTwo, city, country, phone, postalCode));

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
