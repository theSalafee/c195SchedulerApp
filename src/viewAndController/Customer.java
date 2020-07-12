package viewAndController;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Naasir Bush
 */
public class Customer {
    public AnchorPane customerView;
    public Button backBtn;
    public Button addCustomerBtn;
    public Button modifyCustomerBtn;
    public Button deleteCustomerBtn;
    public TableView customerTable;
    public TableColumn custName;
    public TableColumn custAddress1;
    public TableColumn custAddress2;
    public TableColumn custCity;
    public TableColumn custCounty;
    public TableColumn custPhone;
    public TableColumn custPostalCode;

    public void backBtnHandler(ActionEvent actionEvent) {
    }

    public void addHandler(ActionEvent actionEvent) {
    }

    public void modifyHandler(ActionEvent actionEvent) {
    }

    public void deleteHandler(ActionEvent actionEvent) {
    }
}
