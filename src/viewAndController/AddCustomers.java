package viewAndController;

import database.AppointmentDB;
import database.CityDB;
import database.CountryDB;
import database.CustomerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.City;
import models.Country;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable {
    public TextField addressTwo;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField customerName;
    public TextField addressOne;
    public ComboBox<City> city;
    public ComboBox<Country> country;
    public TextField phone;
    public TextField postalCode;

    Stage stage;
    Parent scene;
    static boolean isNewCustomer;
    private Customer selectedCustomer = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isNewCustomer = CustomerController.isIsNewCustomer();
        country.getItems().addAll(CountryDB.getCountries());
        if (!isNewCustomer) {
            selectedCustomer = CustomerController.getSelectedCustomer();
            customerName.setText(selectedCustomer.getCustomerName());
            addressOne.setText(selectedCustomer.getAddress());
            phone.setText(selectedCustomer.getPhone());
            postalCode.setText(selectedCustomer.getPostalCode());
            country.setValue(selectedCustomer.getCustomerCountry());
            city.getItems().clear();
            city.getItems().addAll(CityDB.getCitiesForCountry(selectedCustomer.getCustomerCountry().getCountryId()));
            city.setValue(selectedCustomer.getCity());

        }
    }

    public void saveHandler(ActionEvent actionEvent) throws IOException {

        String customerNameText = customerName.getText();
        if(customerNameText.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Customer");
            alert.setContentText("Error please enter customer name");
            alert.showAndWait();
            return;
        }
        String address = addressOne.getText();
        if(city.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Customer");
            alert.setContentText("Error please select city");
            alert.showAndWait();
            return;
        }
        int cityId = city.getSelectionModel().getSelectedItem().getCityId();
        String phoneText = phone.getText();
        String postalCodeText = postalCode.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WGU Scheduling App");
        alert.setHeaderText("Add Customer");
        alert.setContentText("Are you sure you want to add/modify this customer?");
        alert.showAndWait();

        if (isNewCustomer) {
            CustomerDB.addCustomer(address, cityId, postalCodeText, phoneText, customerNameText);
        } else {
            CustomerDB.updateCustomer(selectedCustomer.getCustomerId(), selectedCustomer.getAddressId(), address, cityId, postalCodeText, phoneText,
                    customerNameText);
        }


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
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    public void handleCountry(ActionEvent actionEvent) {
        city.getItems().clear();
        city.getItems().addAll(CityDB.getCitiesForCountry(country.getValue().getCountryId()));
        city.setValue(null);
    }
}
