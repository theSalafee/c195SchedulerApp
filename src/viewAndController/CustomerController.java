package viewAndController;

import database.CustomerDB;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Naasir Bush
 */
public class CustomerController implements Initializable {
    @FXML
    public AnchorPane customerView;
    @FXML
    public Button backBtn;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Button modifyCustomerBtn;
    @FXML
    public Button deleteCustomerBtn;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, String> customerAddress1;

    @FXML
    public TableColumn<Customer, String> customerCity;
    @FXML
    public TableColumn<Customer, String> customerCountry;
    @FXML
    public TableColumn<Customer, String> customerPhone;
    @FXML
    public TableColumn<Customer, String> customerPostalCode;

    Stage stage;
    Parent scene;

    static boolean isNewCustomer;
    static Customer selectedCustomer;

    public void backBtnHandler(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewAndController/mainMenu.fxml"));
        loader.load();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addHandler(ActionEvent actionEvent) throws IOException {

        isNewCustomer = true;

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/addCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void modifyHandler(ActionEvent actionEvent){

        isNewCustomer = false;
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Something Went Wrong");
            alert.setContentText("Please Select a Customer.");
            alert.showAndWait();
            return;
        }

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        try {
            scene = FXMLLoader.load(getClass().getResource("/viewAndController/addCustomers.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deleteHandler(ActionEvent actionEvent) {
    }

    public static boolean isIsNewCustomer() {
        return isNewCustomer;
    }

    public void convertCustomerString() {

    }

    public void setCustomerList() {
        customerTable.setItems(CustomerDB.getActiveCustomers());

    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setCustomerList();
        convertCustomerString();

        customerName.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });

        customerAddress1.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAddress());
        });

        customerCity.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCity().getCity());
        });

        customerPhone.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getPhone());
        });

        customerPostalCode.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getPostalCode());
        });


        customerCountry.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerCountry());
        });
    }
}
