package viewAndController;

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

    //added 5/25/20
    private final Country custCountry = new Country();


    Stage stage;
    Parent scene;
    static boolean isNewCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isNewCustomer = CustomerController.isIsNewCustomer();

        if(!isNewCustomer){
            Customer selectedCustomer = CustomerController.getSelectedCustomer();
            customerName.setText(selectedCustomer.getCustomerName());
            addressOne.setText(selectedCustomer.getAddress());
            phone.setText(selectedCustomer.getPhone());
            postalCode.setText(selectedCustomer.getPostalCode());
            custCountry.setCountry(selectedCustomer.getCustomerCountry());
        }
    }
    public void saveHandler(ActionEvent actionEvent) throws IOException {

        String customerNameText = customerName.getText();
        String addressOneText = addressOne.getText();
        String addressTwoText = addressTwo.getText();
        String cityText = city.getAccessibleText();
        String countryText = country.getAccessibleText();
        String phoneText = phone.getText();
        String postalCodeText = postalCode.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WGU Scheduling App");
        alert.setHeaderText("Add Customer");
        alert.setContentText("Are you sure you want to add this customer?");
        alert.showAndWait();

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
