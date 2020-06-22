package database;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import models.Appointment;
import models.City;
import models.Country;
import models.Customer;
import static viewAndController.Login.loggedUser;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import static database.DbConnection.conn;

/**
 * This class contains the data access objects (DAOs)
 * for the appointment table in the MySQL database.
 */
public class AppointmentDB {
    private static final ZoneId zId = ZoneId.systemDefault();

    public static ObservableList<Appointment> getApptsByWeek() {
        ObservableList<Appointment> apptsByWeek = FXCollections.observableArrayList();
        String getApptsByWeekSQL = "SELECT customer.*, appointment.* FROM customer "
                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
                + "WHERE start BETWEEN NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY))";

        try {
            PreparedStatement stmt = conn.prepareStatement(getApptsByWeekSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                int appointmentId = rs.getInt("appointmentId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                String customerName = rs.getString("customerName");
                Customer c = CustomerDB.getActiveCustomerById(customerId);

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime start = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime end = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                Appointment activeCustomer = new Appointment(appointmentId, customerId, userId, type, start, end, c);

                apptsByWeek.add(activeCustomer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByWeek;
    }
    public static ObservableList<Appointment> filterappointments() {
        ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();
        String getApptsByMonthSQL = "SELECT customer.*, appointment.* FROM customer "
                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
                + "WHERE start BETWEEN NOW() AND (SELECT LAST_DAY(NOW()))";

        try {
            PreparedStatement stmt = conn.prepareStatement(getApptsByMonthSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                int appointmentId = rs.getInt("appointmentId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                String customerName = rs.getString("customerName");
                Customer c = CustomerDB.getActiveCustomerById(customerId);

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime start = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime end = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                Appointment activeCustomer = new Appointment(appointmentId, customerId, userId, type, start, end, c);

                apptsByMonth.add(activeCustomer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByMonth;
    }


    public static ObservableList<Appointment> getApptsByUser() {
        ObservableList<Appointment> apptsByUser = FXCollections.observableArrayList();
        String getApptsByUserSQL = "SELECT user.userId, customer.customerId, appointment.start FROM user JOIN appointment " +
                "ON user.userId = appointment.userId " +
                "JOIN customer ON appointment.customerId = customer.customerId";

        try {
            PreparedStatement stmt = conn.prepareStatement(getApptsByUserSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                int appointmentId = rs.getInt("appointmentId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                String customerName = rs.getString("customerName");
                Customer c = CustomerDB.getActiveCustomerById(customerId);

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime start = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime end = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                Appointment activeCustomer = new Appointment(appointmentId, customerId, userId, type, start, end, c);

                apptsByUser.add(activeCustomer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByUser;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "Select * from appointment, customer, user Where appointment.customerId = customer.customerId and appointment.userId = user.userId";
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int customerId = rs.getInt("customer.customerId");
                int appointmentId = rs.getInt("appointmentId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                String customerName = rs.getString("customerName");
                Customer c = CustomerDB.getActiveCustomerById(customerId);

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime start = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime end = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                Appointment activeCustomer = new Appointment(appointmentId, customerId, userId, type, start, end, c);

                allAppointments.add(activeCustomer);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }

//    public Appointment getApptById(int appointmentId) {
//        String getApptByIdSQL = "SELECT customer.customerId, customer.customerName, appointment.* FROM customer "
//                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
//                + "WHERE appointmentId = ?";
//        Appointment getApptById = new Appointment();
//        try {
//            PreparedStatement stmt = conn.prepareStatement(getApptsByUserSQL);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                int customerId = rs.getInt("customerId");
//                int appointmentId = rs.getInt("appointmentId");
//                int userId = rs.getInt("userId");
//                String type = rs.getString("type");
//                String customerName = rs.getString("customerName");
//                Customer c = CustomerDB.getActiveCustomerById(customerId);
//
//                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
//                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
//                ZonedDateTime start = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
//                ZonedDateTime end = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
//                Appointment activeCustomer = new Appointment(appointmentId, customerId, userId, type, start, end, c);
//
//                apptsByUser.add(activeCustomer);
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return getApptById;
//    }

    public static ObservableList<Appointment> getUpcomingAppt() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Appointment> filteredData = new FilteredList<>(AppointmentDB.getAllAppointments());
        filteredData.setPredicate(row -> {

            LocalDateTime rowDate = row.getStart().toLocalDateTime();

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus7);
        });
        ObservableList<Appointment> getUpcoming = FXCollections.observableArrayList();
        getUpcoming.addAll(filteredData);
        return getUpcoming;
    }
//        String getUpcomingApptSQL = "SELECT customer.customerName, appointment.* FROM appointment "
//                + "JOIN customer ON appointment.customerId = customer.customerId "
//                + "WHERE (start BETWEEN NOW() AND ADDTIME(NOW(), '00:15:00'))";
//
//        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
//        try {
//            PreparedStatement stmt = conn.prepareStatement(getUpcomingApptSQL);
//            ZonedDateTime localZT = ZonedDateTime.now(zId);
//            ZonedDateTime zdtUTC = localZT.withZoneSameInstant(ZoneId.of("UTC"));
//            LocalDateTime localUTC = zdtUTC.toLocalDateTime();
//            stmt.setTimestamp(1, Timestamp.valueOf(localUTC));
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
////                Customer cust = new Customer();
////                cust.setCustomerName(rs.getString("customerName"));
////                upcomingAppt.setCustomer(cust);
//                upcomingAppt.setAppointmentId(rs.getInt("appointmentId"));
//                upcomingAppt.setCustomerId(rs.getInt("customerId"));
//                upcomingAppt.setUserId(rs.getInt("userId"));
//                upcomingAppt.setTitle(rs.getString("title"));
//                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
//                ZonedDateTime startZDT = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
//
//
//                upcomingAppt.setStart(startZDT);
//                upcomingAppointments.add(upcomingAppt);
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return upcomingAppointments;
//    }

    public static ObservableList<Appointment> getOverlappingAppts(LocalDateTime start, LocalDateTime end) {
//        ObservableList<Appointment> getOverlappedAppts = FXCollections.observableArrayList();
//        String getOverlappingApptsSQL = "SELECT * FROM appointment "
//                + "WHERE (start >= ? AND end <= ?) "
//                + "OR (start <= ? AND end >= ?) "
//                + "OR (start BETWEEN ? AND ? OR end BETWEEN ? AND ?)";
//
//        try {
//            LocalDateTime startLDT = start.atZone(zId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
//            LocalDateTime endLDT = end.atZone(zId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
//            PreparedStatement stmt = conn.prepareStatement(getOverlappingApptsSQL);
//            stmt.setTimestamp(1, Timestamp.valueOf(startLDT));
//            stmt.setTimestamp(2, Timestamp.valueOf(endLDT));
//            stmt.setTimestamp(3, Timestamp.valueOf(startLDT));
//            stmt.setTimestamp(4, Timestamp.valueOf(endLDT));
//            stmt.setTimestamp(5, Timestamp.valueOf(startLDT));
//            stmt.setTimestamp(6, Timestamp.valueOf(endLDT));
//            stmt.setTimestamp(7, Timestamp.valueOf(startLDT));
//            stmt.setTimestamp(8, Timestamp.valueOf(endLDT));
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Appointment overlappedAppt = new Appointment();
//                overlappedAppt.setAppointmentId(rs.getInt("appointmentId"));
//                overlappedAppt.setTitle(rs.getString("title"));
//                overlappedAppt.setDescription(rs.getString("description"));
//                overlappedAppt.setLocation(rs.getString("location"));
//                overlappedAppt.setContact(rs.getString("contact"));
//                overlappedAppt.setType(rs.getString("type"));
//                overlappedAppt.setUrl(rs.getString("url"));
//                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
//                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
//                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
//                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
//                overlappedAppt.setStart(startLocal);
//                overlappedAppt.setEnd(endLocal);
//                getOverlappedAppts.add(overlappedAppt);
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
        //return getOverlappedAppts;
        return null;
    }

//    private static int getMaxAppointmentId() {
//        int maxAppointmentId = 0;
//        String maxAppointmentIdSQL = "SELECT MAX(appointmentId) FROM appointment";
//
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(maxAppointmentIdSQL);
//
//            if (rs.next()) {
//                maxAppointmentId = rs.getInt(1);
//            }
//        }
//        catch (SQLException e) {
//        }
//        return maxAppointmentId + 1;
//    }

    public static void addAppointment(int customerId, int userId, String type, ZonedDateTime start, ZonedDateTime end) {
        String addAppointmentSQL = String.join(" ",
                "INSERT INTO appointment (customerId, userId, title, "
                        + "description, location, contact, type, url, start, end, "
                        + "createDate, createdBy, lastUpdate, lastUpdateBy) ",
                "VALUES (?, ?, '', '', '', '', ?, '', ?, ?, NOW(), ?, NOW(), ?)");

        try {
            PreparedStatement stmt = conn.prepareStatement(addAppointmentSQL);
            stmt.setObject(1, customerId);
            stmt.setObject(2, userId);
            stmt.setObject(3, type);

//            ZonedDateTime startZDT = start.withZoneSameInstant(ZoneId.of("UTC"));
//            ZonedDateTime endZDT = end.withZoneSameInstant(ZoneId.of("UTC"));
            stmt.setTimestamp(4, Timestamp.valueOf(start.toLocalDateTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(end.toLocalDateTime()));

            stmt.setString(6, loggedUser.getUserName());
            stmt.setString(7, loggedUser.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
//    public static void updateAppointment(Appointment appointment) {
//        String updateApptSQL = String.join(" ",
//                "UPDATE appointment",
//                "SET customerId=?, userId=?, title=?, description=?, location=?," +
//                        "contact=?, type=?, url=?, start=?, end=?, lastUpdate=NOW(), lastUpdateBy=?",
//                "WHERE appointmentId=?");

//        try {
//            PreparedStatement stmt = conn.prepareStatement(updateApptSQL);
//            stmt.setObject(1, appointment.getCustomerId());
//            stmt.setObject(2, appointment.getUserId());
//            stmt.setObject(3, appointment.getTitle());
//            stmt.setObject(4, appointment.getDescription());
//            stmt.setObject(5, appointment.getLocation());
//            stmt.setObject(6, appointment.getContact());
//            stmt.setObject(7, appointment.getType());
//            stmt.setObject(8, appointment.getUrl());
//
//            ZonedDateTime startZDT = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
//            ZonedDateTime endZDT = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
//            stmt.setTimestamp(9, Timestamp.valueOf(startZDT.toLocalDateTime()));
//            stmt.setTimestamp(10, Timestamp.valueOf(endZDT.toLocalDateTime()));
//
//            stmt.setString(11, loggedUser.getUserName());
//            stmt.setObject(12, appointment.getAppointmentId());
//            stmt.executeUpdate();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
 //   }
        public static void deleteAppointment (Appointment appointment){
            String deleteAppointmentSQL = "DELETE FROM appointment WHERE appointmentId = ?";

            try {
                PreparedStatement stmt = conn.prepareStatement(deleteAppointmentSQL);
                stmt.setObject(1, appointment.getAppointmentId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
