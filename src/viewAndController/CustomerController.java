package viewAndController;
import database.CustomerDB;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TableColumn<Customer, String> customerAddress2;
    @FXML
    public TableColumn<Customer, String> customerCity;
    @FXML
    public TableColumn<Customer, String> customerCounty;
    @FXML
    public TableColumn<Customer, String> customerPhone;
    @FXML
    public TableColumn<Customer, String> customerPostalCode;

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

//        customerCounty.setCellValueFactory(cellData -> {
//            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerCountry());
//        });
    }
}
