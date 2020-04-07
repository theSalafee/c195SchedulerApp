package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import static sample.Login.loggedUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static database.DbConnection.conn;

public class CustomerDB {

    /**
     * This method creates an ObservableList and populates it with
     * all currently active Customer records in the MySQL database.
     * @return activeCustomers
     */
    public static ObservableList<Customer> getActiveCustomers() {
        ObservableList<Customer> activeCustomers = FXCollections.observableArrayList();
        String getActiveCustomersSQL = "SELECT * FROM customer WHERE active = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(getActiveCustomersSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer activeCustomer = new Customer();
                activeCustomer.setCustomerId(rs.getInt("customerId"));
                activeCustomer.setCustomerName(rs.getString("customerName"));
                activeCustomer.setAddressId(rs.getInt("addressId"));
                activeCustomer.setActive(rs.getBoolean("active"));
                activeCustomers.add(activeCustomer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return activeCustomers;
    }

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCusts = FXCollections.observableArrayList();
        String getAllCustsSQL = "SELECT * FROM customer";

        try {
            PreparedStatement stmt = conn.prepareStatement(getAllCustsSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("customerId"));
                cust.setCustomerName(rs.getString("customerName"));
                cust.setAddressId(rs.getInt("addressId"));
                cust.setActive(rs.getBoolean("active"));
                allCusts.add(cust);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allCusts;
    }

    /**
     * This method gets a Customer record from the MySQL database by customerId.
     * @param customerId
     * @return getCustomerQuery
     */
    public static Customer getActiveCustomerById(int customerId) {
        String getCustomerByIdSQL = "SELECT * FROM customer WHERE customerId = ? AND active=1";
        Customer getCustomerQuery = new Customer();

        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerByIdSQL);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                getCustomerQuery.setCustomerId(rs.getInt("customerId"));
                getCustomerQuery.setCustomerName(rs.getString("customerName"));
                getCustomerQuery.setAddressId(rs.getInt("addressId"));
                getCustomerQuery.setActive(rs.getBoolean("active"));
                getCustomerQuery.setLastUpdate(rs.getTimestamp("lastUpdate"));
                getCustomerQuery.setLastUpdateBy(rs.getString("lastUpdateBy"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getCustomerQuery;
    }

    private static int getMaxCustomerId() {
        int maxCustomerId = 0;
        String maxCustomerIdSQL = "SELECT MAX(customerId) FROM customer";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(maxCustomerIdSQL);

            if (rs.next()) {
                maxCustomerId = rs.getInt(1);
            }
        }
        catch (SQLException e) {
        }
        return maxCustomerId + 1;
    }

    /**
     * This method adds a new Customer to the MySQL database.
     * @param customer
     * @return customer
     */
    public static Customer addCustomer(Customer customer) {
        String addCustomerSQL = String.join(" ",
                "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, 1, NOW(), ?, NOW(), ?)");

        int customerId = getMaxCustomerId();
        try {
            PreparedStatement stmt = conn.prepareStatement(addCustomerSQL);
            stmt.setInt(1, customerId);
            stmt.setString(2, customer.getCustomerName());
            stmt.setInt(3, customer.getAddressId());
            stmt.setString(4, loggedUser.getUserName());
            stmt.setString(5, loggedUser.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * This method updates an existing Customer record in the MySQL database.
     * @param customer
     */
    public static void updateCustomer(Customer customer) {
        String updateCustomerSQL = String.join(" ",
                "UPDATE customer",
                "SET customerName=?, addressId=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE customerId = ?");

        try {
            PreparedStatement stmt = conn.prepareStatement(updateCustomerSQL);
            stmt.setString(1, customer.getCustomerName());
            stmt.setInt(2, customer.getAddressId());
            stmt.setString(3, loggedUser.getUserName());
            stmt.setInt(4, customer.getCustomerId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method soft deletes an existing Customer from the MySQL database
     * by setting the active property to 0.
     * @param customer
     */
    public static void deleteCustomer(Customer customer) {
        String deleteCustomerSQL = "UPDATE customer SET active=0 WHERE customerId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(deleteCustomerSQL);
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
