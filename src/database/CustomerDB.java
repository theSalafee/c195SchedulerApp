package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.City;
import models.Customer;
import static viewAndController.Login.loggedUser;
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
        String getActiveCustomersSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE active=1 AND customer.addressId = address.addressId " +
                "AND address.cityId = city.cityId and city.countryId = country.countryId";
        try {
            PreparedStatement stmt = conn.prepareStatement(getActiveCustomersSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Customer activeCustomer = new Customer();
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId= rs.getInt("address.addressId");
                int active = rs.getInt("active");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city"), rs.getInt("country.countryId"));
                String address  = rs.getString("address");
                String address2 = rs.getString("address.address2");
                String customerCountry = rs.getString("country");
                Customer activeCustomer = new Customer(customerId, customerName, addressId, phone, postalCode, city, address, customerCountry, active);
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
        String getAllCustsSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE active=1 AND customer.addressId = address.addressId " +
                "AND address.cityId = city.cityId and city.countryId = country.countryId";

        try {
            PreparedStatement stmt = conn.prepareStatement(getAllCustsSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId= rs.getInt("address.addressId");
                int active = rs.getInt("active");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city"), rs.getInt("country.countryId"));
                String address  = rs.getString("address");
                String customerCountry = rs.getString("country");
                Customer allCustomers = new Customer(customerId, customerName, addressId, phone, postalCode, city, address, customerCountry, active);
                allCusts.add(allCustomers);
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
        String getCustomerByIdSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE customer.customerId = ? AND active=1 AND customer.addressId = address.addressId AND address.cityId = city.cityId and city.countryId = country.countryId";

        Customer c = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerByIdSQL);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city.city"), rs.getInt("city.countryId"));
                String address = rs.getString("address");
                String country = rs.getString("country");
                c = new Customer(customerId,  customerName,  addressId,  phone,  postalCode,  city,  address,  country,  1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
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
