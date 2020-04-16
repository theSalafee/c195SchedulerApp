package sample;
import database.CustomerDB;
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
    public TableView customerTable;
    @FXML
    public TableColumn customerName;
    @FXML
    public TableColumn customerAddress1;
    @FXML
    public TableColumn customerAddress2;
    @FXML
    public TableColumn customerCity;
    @FXML
    public TableColumn customerCounty;
    @FXML
    public TableColumn customerPhone;
    @FXML
    public TableColumn customerPostalCode;

    @FXML
    //private ListView<Customer> lvCustomer;

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



    public void convertCustomerString() {
//        lvCustomer.setCellFactory(c -> new ListCell<CustomerController>() {
//            @Override
//            protected void updateItem(CustomerController item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item.getCustomerName() == null) {
//                    setText("");
//                }
//                else {
//                    setText(item.getCustomerName());
//                }
//            }
//        });
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

        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        //customerAddress1.setCellValueFactory(new PropertyValueFactory<>("customerAddress1"));
        //customerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
        //customerCity.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        //customerPhone.setCellFactory(new PropertyValueFactory<>("customerPhone"));
        //customerPostalCode.setCellFactory(new PropertyValueFactory<>("customerPostalCode"));

    }
}
